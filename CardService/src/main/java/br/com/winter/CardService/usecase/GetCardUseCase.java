package br.com.winter.CardService.usecase;

import br.com.winter.CardService.entity.Card;
import br.com.winter.CardService.exceptions.CardDoesntExistsException;
import br.com.winter.CardService.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetCardUseCase {

    private final ICardRepository repository;

    @Autowired
    public GetCardUseCase(ICardRepository repository) {
        this.repository = repository;
    }

    public Page<Card> getAllCards(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Card> getCardById(UUID id) {
        Optional<Card> clientOptional = repository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new CardDoesntExistsException("The Card with id: " + id + " doesn't exists");
        }
        return clientOptional;
    }

    public Optional<Card> getCardByName(String name) {
        Optional<Card> clientOptional = repository.findByName(name);
        if (clientOptional.isEmpty()) {
            throw new CardDoesntExistsException("The Card with Name: " + name + " doesn't exists");
        }
        return clientOptional;
    }

    public Optional<Card> getCardByLessThan(Long income) {
        var incomeBigDecimal = BigDecimal.valueOf(income);
        return repository.findByIncomeLessThanEqual(incomeBigDecimal);
    }

}
