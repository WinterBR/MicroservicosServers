package br.com.winter.CardService.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CardEmissionSolicitationData {
    private UUID idCard;
    private String cpf;
    private String address;
    private BigDecimal liberatedLimit;
}
