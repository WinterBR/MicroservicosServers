package br.com.winter.CardService.repository;

import br.com.winter.CardService.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByBand(String band);

    Optional<Card> findByName(String name);

    void deleteByName(String name);

    Optional<Card> findByIncomeLessThanEqual(BigDecimal income);
}
