package br.com.winter.ClienteService.usecase;

import br.com.winter.ClienteService.exceptions.ClientDoesntExistsException;
import br.com.winter.ClienteService.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteClientUseCase {

    private final IClientRepository repository;

    @Autowired
    public DeleteClientUseCase(IClientRepository respository) {
        this.repository = respository;
    }

    @Transactional
    public void deleteById(UUID id) {
        if(repository.findById(id).isEmpty()) {
            throw new ClientDoesntExistsException("The Client with id: " + id + " doesn't exists");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteByCpf(String cpf) {
        if(repository.findByCpf(cpf).isEmpty()) {
            throw new ClientDoesntExistsException("The Client with CPF: " + cpf + " doesn't exists");
        }
        repository.deleteByCpf(cpf);
    }
}
