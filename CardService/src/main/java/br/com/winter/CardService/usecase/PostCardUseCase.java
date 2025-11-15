package br.com.winter.CardService.usecase;

import br.com.winter.CardService.entity.Card;
import br.com.winter.CardService.repository.ICardRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostCardUseCase {

    private final ICardRepository repository;

    @Autowired
    public PostCardUseCase(ICardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Card postCard(@Valid Card client) {
        return repository.save(client);
    }
}
