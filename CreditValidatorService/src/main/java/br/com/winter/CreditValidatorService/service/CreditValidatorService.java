package br.com.winter.CreditValidatorService.service;

import br.com.winter.CreditValidatorService.entity.*;
import br.com.winter.CreditValidatorService.exceptions.CardSolicitationErrorException;
import br.com.winter.CreditValidatorService.exceptions.ClientDataNotFoundException;
import br.com.winter.CreditValidatorService.exceptions.MicroserviceCommunicationException;
import br.com.winter.CreditValidatorService.infra.clients.ICardResourceClient;
import br.com.winter.CreditValidatorService.infra.clients.IClientResourceClient;
import br.com.winter.CreditValidatorService.infra.queue.CardEmissionSolicitationPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import feign.Response;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class CreditValidatorService {

    private final IClientResourceClient clientResourceClient;
    private final ICardResourceClient cardResourceClient;
    private final CardEmissionSolicitationPublisher emissionSolicitationPublisher;

    @Autowired
    public CreditValidatorService(IClientResourceClient clientResourceClient, ICardResourceClient cardResourceClient, CardEmissionSolicitationPublisher emissionSolicitationPublisher) {
        this.clientResourceClient = clientResourceClient;
        this.cardResourceClient = cardResourceClient;
        this.emissionSolicitationPublisher = emissionSolicitationPublisher;
    }

    public ClientSituation getClientSituation(String cpf) {
        try {
            ResponseEntity<ClientData> clientDataResponseEntity = clientResourceClient.getClientCpf(cpf);
            ResponseEntity<List<ClientCard>> clientCardResponseEntity = cardResourceClient.getCardsByClientCpf(cpf);
            return ClientSituation.builder()
                    .client(clientDataResponseEntity.getBody())
                    .cards(clientCardResponseEntity.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new ClientDataNotFoundException();
            }
            throw new MicroserviceCommunicationException(e.getMessage(), status);
        }
    }

    public ClientValidationReturn performReview(String cpf, Long income) {
        try {
            ResponseEntity<ClientData> clientDataResponse = clientResourceClient.getClientCpf(cpf);
            ResponseEntity<List<Card>> cardsResponse =  cardResourceClient.getCardByIncomeLessThan(income);

            List<Card> cards = cardsResponse.getBody();
            var approvedCardList =  cards.stream().map(c -> {

                BigDecimal basicLimit = c.getLimit();
                LocalDate dateAge = clientDataResponse.getBody().getAge();
                BigDecimal ageDB = BigDecimal.valueOf(Period.between(dateAge, LocalDate.now()).getYears());
                var factor = ageDB.divide(BigDecimal.valueOf(10));
                BigDecimal approvedLimit = factor.multiply(basicLimit);

                ApprovedCards approved = new ApprovedCards();
                approved.setCard(c.getName());
                approved.setBrand(c.getBrand());
                approved.setApprovedLimit(approvedLimit);

                return approved;
            }).toList();

            return new ClientValidationReturn(approvedCardList);

        } catch (FeignException.FeignClientException e) {
        int status = e.status();
        if(HttpStatus.NOT_FOUND.value() == status) {
            throw new ClientDataNotFoundException();
        }
        throw new MicroserviceCommunicationException(e.getMessage(), status);
    }

    }

    public Object requestCardEmission(CardEmissionSolicitationData data) {
        try {
            emissionSolicitationPublisher.requestCard(data);
            var protocol = UUID.randomUUID().toString();
            return new CardSolicitationProtocol(protocol);
        } catch (Exception e) {
            throw new CardSolicitationErrorException(e.getMessage());
        }
    }

}
