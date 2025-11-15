package br.com.winter.CardService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCard {

    @Id
    private UUID id;
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card card;
    private BigDecimal limit;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

}
