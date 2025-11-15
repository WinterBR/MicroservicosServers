package br.com.winter.CardService.mapper;

import br.com.winter.CardService.dto.CardRequestDTO;
import br.com.winter.CardService.dto.CardResponseDTO;
import br.com.winter.CardService.entity.Card;

public class CardMapper {

    public static Card toEntity(CardRequestDTO dto) {
        if (dto == null) return null;
        return Card.builder()
                .name(dto.name())
                .brand(dto.brand())
                .income(dto.income())
                .limit(dto.limit())
                .build();
    }

    public static CardResponseDTO toDTO(Card entity) {
        if (entity == null) return null;
        return new CardResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getBrand(),
                entity.getIncome(),
                entity.getLimit()
        );
    }
}
