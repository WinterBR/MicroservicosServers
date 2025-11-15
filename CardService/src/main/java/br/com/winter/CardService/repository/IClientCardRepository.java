package br.com.winter.CardService.repository;

import br.com.winter.CardService.entity.ClientCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IClientCardRepository extends JpaRepository<ClientCard, UUID> {

    Optional<ClientCard> findByCpf(String cpf);
}
