package br.com.winter.CardService.usecase;

import br.com.winter.CardService.entity.Card;
import br.com.winter.CardService.exceptions.CardAlreadyExistsException;
import br.com.winter.CardService.exceptions.CardDoesntExistsException;
import br.com.winter.CardService.repository.ICardRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PutCardUseCase {

    private final ICardRepository repository;

    @Autowired
    public PutCardUseCase(ICardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Card putCard(UUID id, @Valid Card client)  {
        Optional<Card> clientOptional = repository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new CardDoesntExistsException("The Card with id: " + id + " doesn't exists");
        }
        Card clientExists = clientOptional.get();
        if (client.getName() != null) {
            clientExists.setName(client.getName());
        }
        if (client.getIncome() != null) {
            clientExists.setIncome(client.getIncome());
        }
        if (client.getLimit() != null) {
            clientExists.setLimit(client.getLimit());
        }
        return repository.save(clientExists);
    }
}
