package br.com.winter.ClienteService.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {

    private UUID id;
    private String name;
    private LocalDate age;
    private String cpf;
}
