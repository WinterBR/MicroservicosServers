package br.com.winter.ClienteService.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {

    @NotBlank
    private String name;

    @Past
    @NotNull
    private LocalDate age;

    @Size(max = 11, min = 11)
    @NotBlank
    private String cpf;

}
