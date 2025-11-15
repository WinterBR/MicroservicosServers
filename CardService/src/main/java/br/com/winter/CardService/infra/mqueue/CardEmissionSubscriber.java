package br.com.winter.CardService.infra.mqueue;

import br.com.winter.CardService.entity.Card;
import br.com.winter.CardService.entity.CardEmissionSolicitationData;
import br.com.winter.CardService.entity.ClientCard;
import br.com.winter.CardService.repository.ICardRepository;
import br.com.winter.CardService.repository.IClientCardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CardEmissionSubscriber {

    private final ICardRepository cardRepository;
    private final IClientCardRepository clientCardRepository;

    public CardEmissionSubscriber(ICardRepository cardRepository, IClientCardRepository clientCardRepository) {
        this.cardRepository = cardRepository;
        this.clientCardRepository = clientCardRepository;
    }

    @RabbitListener(queues = "${mq.queues.cards-emission}")
    public void receiveEmissionSolicitation(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();
            CardEmissionSolicitationData data = mapper.readValue(payload, CardEmissionSolicitationData.class);
            Card card = cardRepository.findById(data.getIdCard()).orElseThrow();
            ClientCard clientCard = new ClientCard();
            clientCard.setCard(card);
            clientCard.setCpf(data.getCpf());
            clientCard.setLimit(data.getLiberatedLimit());

            clientCardRepository.save(clientCard);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
