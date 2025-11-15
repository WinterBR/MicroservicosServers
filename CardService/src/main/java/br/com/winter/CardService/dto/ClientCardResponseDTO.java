package br.com.winter.CardService.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ClientCardResponseDTO(
        UUID id,
        String cpf,
        UUID cardId,
        String cardName,
        BigDecimal limit
) {}
