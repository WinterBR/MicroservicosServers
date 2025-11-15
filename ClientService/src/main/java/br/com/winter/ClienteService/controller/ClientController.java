package br.com.winter.ClienteService.controller;

import br.com.winter.ClienteService.dto.ClientRequestDTO;
import br.com.winter.ClienteService.dto.ClientResponseDTO;
import br.com.winter.ClienteService.entity.Client;
import br.com.winter.ClienteService.repository.IClientRepository;
import br.com.winter.ClienteService.usecase.PostClientUseCase;
import jakarta.validation.Valid;
import br.com.winter.ClienteService.mapper.ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.winter.ClienteService.usecase.DeleteClientUseCase;
import br.com.winter.ClienteService.usecase.GetClientUseCase;
import br.com.winter.ClienteService.usecase.PutClientUseCase;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("clients")
@Slf4j
public class ClientController {

    private final DeleteClientUseCase delete;
    private final GetClientUseCase get;
    private final PostClientUseCase post;
    private final PutClientUseCase put;
    private final IClientRepository repository;

    @Autowired
    public ClientController(
            DeleteClientUseCase delete,
            GetClientUseCase get,
            PostClientUseCase post,
            PutClientUseCase put,
            IClientRepository repository) {
        this.delete = delete;
        this.get = get;
        this.post = post;
        this.put = put;
        this.repository = repository;
    }

    @GetMapping
    public String status() {
        log.info("Getting the clients microservice status");
        return "ok";
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> getAllClients(Pageable pageable) {
        Page<ClientResponseDTO> page = get.getAllClients(pageable)
                .map(ClientMapper::toDTO);
        return ResponseEntity.ok(page); // 200
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClientResponseDTO> getClientId(@PathVariable UUID id) {
        return get.getClientById(id)
                .map(ClientMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClientResponseDTO> getClientCpf(@PathVariable String cpf) {
        return get.getClientByCpf(cpf)
                .map(ClientMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> postClient(@RequestBody @Valid ClientRequestDTO dto) {
        Client client = ClientMapper.toEntity(dto);
        Client saved = post.postClient(client);
        ClientResponseDTO response = ClientMapper.toDTO(saved);

        return ResponseEntity
                .created(URI.create("/clients/id/" + saved.getId()))
                .body(response); // 201
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ClientResponseDTO> putClient(
            @PathVariable UUID id,
            @RequestBody @Valid ClientRequestDTO dto) {

        return repository.findById(id)
                .map(existing -> {
                    Client updatedEntity = ClientMapper.toEntity(dto);
                    Client updated = put.putClient(id, updatedEntity);
                    return ResponseEntity.ok(ClientMapper.toDTO(updated)); // 200
                })
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            delete.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
