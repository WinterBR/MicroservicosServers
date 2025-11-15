package br.com.winter.CardService.usecase;

import br.com.winter.CardService.entity.ClientCard;
import br.com.winter.CardService.exceptions.ClientCardDoesntExistsException;
import br.com.winter.CardService.repository.IClientCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetClientCardUseCase {

    private final IClientCardRepository repository;

    @Autowired
    public GetClientCardUseCase(IClientCardRepository repository) {
        this.repository = repository;
    }

    public Optional<ClientCard> getClientCardByCpf(String cpf) {
        Optional<ClientCard> clientOptional = repository.findByCpf(cpf);
        if (clientOptional.isEmpty()) {
            throw new ClientCardDoesntExistsException("The Client with CPF: " + cpf + " doesn't exists");
        }
        return repository.findByCpf(cpf);
    }
}
