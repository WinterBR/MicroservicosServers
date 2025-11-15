package br.com.winter.CardService.dto;

import br.com.winter.CardService.enums.CardBrand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CardRequestDTO(
        @NotEmpty String name,
        @NotNull CardBrand brand,
        @NotNull BigDecimal income,
        @NotNull BigDecimal limit
) {}
