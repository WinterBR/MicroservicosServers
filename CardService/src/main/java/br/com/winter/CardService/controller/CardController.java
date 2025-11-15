package br.com.winter.CardService.controller;

import br.com.winter.CardService.dto.CardRequestDTO;
import br.com.winter.CardService.dto.CardResponseDTO;
import br.com.winter.CardService.dto.ClientCardRequestDTO;
import br.com.winter.CardService.dto.ClientCardResponseDTO;
import br.com.winter.CardService.entity.Card;
import br.com.winter.CardService.entity.ClientCard;
import br.com.winter.CardService.mapper.CardMapper;
import br.com.winter.CardService.mapper.ClientCardMapper;
import br.com.winter.CardService.repository.ICardRepository;
import br.com.winter.CardService.usecase.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("cards")
@Slf4j
public class CardController {

    private final DeleteCardUseCase delete;
    private final GetCardUseCase get;
    private final PostCardUseCase post;
    private final PutCardUseCase put;
    private final ICardRepository repository;
    private final GetClientCardUseCase cget;

    @Autowired
    public CardController(
            DeleteCardUseCase delete,
            GetCardUseCase get,
            PostCardUseCase post,
            PutCardUseCase put,
            ICardRepository repository, GetClientCardUseCase cget) {
        this.delete = delete;
        this.get = get;
        this.post = post;
        this.put = put;
        this.repository = repository;
        this.cget = cget;
    }

    @GetMapping
    public String status() {
        log.info("Getting the cards microservice status");
        return "ok";
    }

    @GetMapping
    public ResponseEntity<Page<CardResponseDTO>> getAllCards(Pageable pageable) {
        Page<CardResponseDTO> page = get.getAllCards(pageable)
                .map(CardMapper::toDTO);
        return ResponseEntity.ok(page); // 200
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CardResponseDTO> getCardId(@PathVariable UUID id) {
        return get.getCardById(id)
                .map(CardMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CardResponseDTO> getCardName(@PathVariable String name) {
        return get.getCardByName(name)
                .map(CardMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("income/{income}")
    public ResponseEntity<CardResponseDTO> getCardByIncomeLessThan(@PathVariable Long income) {
        return get.getCardByLessThan(income)
                .map(CardMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> postCard(@RequestBody @Valid CardRequestDTO dto) {
        Card client = CardMapper.toEntity(dto);
        Card saved = post.postCard(client);
        CardResponseDTO response = CardMapper.toDTO(saved);

        return ResponseEntity
                .created(URI.create("/clients/id/" + saved.getId()))
                .body(response); // 201
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<CardResponseDTO> putCard(
            @PathVariable UUID id,
            @RequestBody @Valid CardRequestDTO dto) {

        return repository.findById(id)
                .map(existing -> {
                    Card updatedEntity = CardMapper.toEntity(dto);
                    Card updated = put.putCard(id, updatedEntity);
                    return ResponseEntity.ok(CardMapper.toDTO(updated)); // 200
                })
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            delete.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<ClientCardResponseDTO>> getCardsByClientCpf(@PathVariable String cpf) {
        List<ClientCard> list = cget.getClientCardByCpf(cpf).stream().toList();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ClientCardResponseDTO> resultList = list.stream()
                .map(ClientCardMapper::toDTO)
                .toList();
        return ResponseEntity.ok(resultList);
    }
}
