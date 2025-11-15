package br.com.winter.CreditValidatorService.exceptions;

public class ClientDataNotFoundException extends RuntimeException {
    public ClientDataNotFoundException() {
        super("Data hasn't been found ");
    }
}
