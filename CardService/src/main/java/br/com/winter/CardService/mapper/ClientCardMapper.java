package br.com.winter.CardService.mapper;

import br.com.winter.CardService.dto.ClientCardRequestDTO;
import br.com.winter.CardService.dto.ClientCardResponseDTO;
import br.com.winter.CardService.entity.Card;
import br.com.winter.CardService.entity.ClientCard;

public class ClientCardMapper {

    public static ClientCard toEntity(ClientCardRequestDTO dto, Card card) {
        if (dto == null || card == null) return null;
        return ClientCard.builder()
                .cpf(dto.cpf())
                .card(card)
                .limit(dto.limit())
                .build();
    }

    public static ClientCardResponseDTO toDTO(ClientCard entity) {
        if (entity == null) return null;
        return new ClientCardResponseDTO(
                entity.getId(),
                entity.getCpf(),
                entity.getCard() != null ? entity.getCard().getId() : null,
                entity.getCard() != null ? entity.getCard().getName() : null,
                entity.getLimit()
        );
    }
}
