package br.com.winter.CardService.entity;

import br.com.winter.CardService.enums.CardBrand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Entity
@Table(name = "card_tb")
public class Card {

    @Id
    private UUID id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private CardBrand brand;

    @NotBlank
    @Column(name = "income")
    private BigDecimal income;

    @NotBlank
    @Column(name = "limit")
    private BigDecimal limit;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

}
