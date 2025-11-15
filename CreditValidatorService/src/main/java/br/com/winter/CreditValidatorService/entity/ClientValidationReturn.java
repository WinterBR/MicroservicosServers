package br.com.winter.CreditValidatorService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientValidationReturn {

    private List<ApprovedCards> cards;
}
