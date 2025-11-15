package br.com.winter.ClienteService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_tb")
public class Client {

    @Id
    private UUID id;

    @NotEmpty
    @Size(max = 11, min = 11)
    @Column(name = "cpf", unique = true)
    private String cpf;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotBlank
    @Past
    @Column(name = "age")
    private LocalDate age;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
