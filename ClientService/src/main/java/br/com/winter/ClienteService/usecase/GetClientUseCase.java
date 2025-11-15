package br.com.winter.ClienteService.usecase;

import br.com.winter.ClienteService.entity.Client;
import br.com.winter.ClienteService.exceptions.ClientDoesntExistsException;
import br.com.winter.ClienteService.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetClientUseCase {

    private final IClientRepository repository;

    @Autowired
    public GetClientUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    public Page<Client> getAllClients(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Client> getClientById(UUID id) {
        Optional<Client> clientOptional = repository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new ClientDoesntExistsException("The Client with id: " + id + " doesn't exists");
        }
        return clientOptional;
    }

    public Optional<Client>  getClientByCpf(String cpf) {
        Optional<Client> clientOptional = repository.findByCpf(cpf);
        if (clientOptional.isEmpty()) {
            throw new ClientDoesntExistsException("The Client with CPF: " + cpf + " doesn't exists");
        }
        return clientOptional;
    }

}
