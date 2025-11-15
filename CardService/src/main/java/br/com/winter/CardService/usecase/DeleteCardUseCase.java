package br.com.winter.CardService.usecase;

import br.com.winter.CardService.exceptions.CardDoesntExistsException;
import br.com.winter.CardService.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteCardUseCase {

    private final ICardRepository repository;

    @Autowired
    public DeleteCardUseCase(ICardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void deleteById(UUID id) {
        if(repository.findById(id).isEmpty()) {
            throw new CardDoesntExistsException("The Card with id: " + id + " doesn't exists");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteByName(String name) {
        if(repository.findByName(name).isEmpty()) {
            throw new CardDoesntExistsException("The Card with Name: " + name + " doesn't exists");
        }
        repository.deleteByName(name);
    }
}
