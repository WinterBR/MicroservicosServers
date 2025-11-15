package br.com.winter.ClienteService.usecase;

import br.com.winter.ClienteService.entity.Client;
import br.com.winter.ClienteService.exceptions.ClientAlreadyExistsException;
import br.com.winter.ClienteService.repository.IClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostClientUseCase {

    private final IClientRepository repository;

    @Autowired
    public PostClientUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client postClient(@Valid Client client) {
        if (repository.findByCpf(client.getCpf()).isPresent()) {
            throw new ClientAlreadyExistsException("The CPF " + client.getCpf() + " already exists");
        }
        return repository.save(client);
    }
}
