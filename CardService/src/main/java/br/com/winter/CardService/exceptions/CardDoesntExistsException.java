package br.com.winter.CardService.exceptions;

public class CardDoesntExistsException extends RuntimeException {
    public CardDoesntExistsException(String message) {
        super(message);
    }
}
