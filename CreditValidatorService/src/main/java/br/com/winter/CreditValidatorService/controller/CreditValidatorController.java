package br.com.winter.CreditValidatorService.controller;

import br.com.winter.CreditValidatorService.entity.*;
import br.com.winter.CreditValidatorService.exceptions.ClientDataNotFoundException;
import br.com.winter.CreditValidatorService.exceptions.MicroserviceCommunicationException;
import br.com.winter.CreditValidatorService.service.CreditValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("validation-credit")
public class CreditValidatorController {

    private final CreditValidatorService service;

    @Autowired
    public CreditValidatorController(CreditValidatorService service) {
        this.service = service;
    }

    @GetMapping
    public String status() {
        log.info("Getting the credit validation microservice status");
        return "ok";
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity getClientCpf(@PathVariable String cpf) {
        try {
            ClientSituation clientSituation = service.getClientSituation(cpf);
            return ResponseEntity.ok(clientSituation);
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceCommunicationException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity performReview(@RequestBody DataValidation dataValidation) {
        try {
            ClientValidationReturn clientValidationReturn = service.performReview(dataValidation.getCpf(), dataValidation.getIncome());
            return ResponseEntity.ok(clientValidationReturn);
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceCommunicationException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("cards-solicitation")
    public ResponseEntity requestCard(@RequestBody CardEmissionSolicitationData data) {
        try {
            CardSolicitationProtocol cardSolicitationProtocol = (CardSolicitationProtocol) service.requestCardEmission(data);
            return ResponseEntity.ok(cardSolicitationProtocol);
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
