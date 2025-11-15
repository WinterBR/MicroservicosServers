package br.com.winter.CreditValidatorService.exceptions;

public class CardSolicitationErrorException extends RuntimeException {
    public CardSolicitationErrorException(String message) {
        super(message);
    }
}
