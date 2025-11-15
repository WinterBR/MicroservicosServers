package br.com.winter.ClienteService.usecase;

import br.com.winter.ClienteService.entity.Client;
import br.com.winter.ClienteService.exceptions.ClientAlreadyExistsException;
import br.com.winter.ClienteService.exceptions.ClientDoesntExistsException;
import br.com.winter.ClienteService.repository.IClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PutClientUseCase {

    private final IClientRepository repository;

    @Autowired
    public PutClientUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client putClient(UUID id, @Valid Client client)  {
        Optional<Client> clientOptional = repository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new ClientDoesntExistsException("The Client with id: " + id + " doesn't exists");
        }
        Client clientExists = clientOptional.get();
        if (client.getName() != null) {
            clientExists.setName(client.getName());
        }
        if (client.getAge() != null) {
            clientExists.setAge(client.getAge());
        }
        if (client.getCpf() != null) {
            clientExists.setCpf(client.getCpf());
        }
        if (client.getCpf() != null &&
                repository.findByCpf(clientExists.getCpf())
                        .filter(c -> !c.getId().equals(clientExists.getId()))
                        .isPresent()) {
            throw new ClientAlreadyExistsException("The CPF " + client.getCpf() + " already exists");
        }

        return repository.save(clientExists);
    }
}
