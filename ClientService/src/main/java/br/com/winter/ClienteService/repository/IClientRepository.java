package br.com.winter.ClienteService.repository;

import br.com.winter.ClienteService.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByCpf(String cpf);

    Optional<Client> deleteByCpf(String cpf);

}
