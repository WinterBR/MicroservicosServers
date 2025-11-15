package br.com.winter.CreditValidatorService.exceptions;

import lombok.Getter;

public class MicroserviceCommunicationException extends RuntimeException {

    @Getter
    private Integer status;

    public MicroserviceCommunicationException(String message, Integer status) {

        super(message);
        this.status = status;
    }
}
