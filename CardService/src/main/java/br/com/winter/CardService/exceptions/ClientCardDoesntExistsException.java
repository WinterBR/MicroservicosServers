package br.com.winter.CardService.exceptions;

public class ClientCardDoesntExistsException extends RuntimeException {
    public ClientCardDoesntExistsException(String message) {
        super(message);
    }
}
