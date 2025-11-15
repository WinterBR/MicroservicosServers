package br.com.winter.ClienteService.exceptions;

public class ClientDoesntExistsException extends RuntimeException {
    public ClientDoesntExistsException(String message) {
        super(message);
    }
}
