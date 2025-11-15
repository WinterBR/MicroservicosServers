package br.com.winter.CardService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record ClientCardRequestDTO(
        @NotEmpty String cpf,
        @NotNull UUID cardId,
        @NotNull BigDecimal limit
) {}
