package br.com.winter.CardService.dto;

import br.com.winter.CardService.enums.CardBrand;
import java.math.BigDecimal;
import java.util.UUID;

public record CardResponseDTO(
        UUID id,
        String name,
        CardBrand brand,
        BigDecimal income,
        BigDecimal limit
) {}
