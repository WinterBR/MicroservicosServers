package br.com.winter.CreditValidatorService.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ClientData {

    private UUID id;

    private String name;

    private LocalDate age;
}
