package br.com.winter.CreditValidatorService.infra.clients;

import br.com.winter.ClienteService.dto.ClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "ClientService", path = "/clients")
public interface IClientResourceClient {

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity getClientCpf(@PathVariable String cpf);

}