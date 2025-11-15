package br.com.winter.CreditValidatorService.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCards {

    private String card;

    private String brand;

    private BigDecimal approvedLimit;
}
