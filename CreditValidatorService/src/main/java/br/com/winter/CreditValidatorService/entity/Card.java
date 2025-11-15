package br.com.winter.CreditValidatorService.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Card {
    private UUID id;

    private String name;

    private String brand;

    private BigDecimal limit;
}
