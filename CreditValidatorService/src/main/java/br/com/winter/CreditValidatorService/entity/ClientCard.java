package br.com.winter.CreditValidatorService.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientCard {

    private String name;

    private String brand;

    private BigDecimal limit;
}
