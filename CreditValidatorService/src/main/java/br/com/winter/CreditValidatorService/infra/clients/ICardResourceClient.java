package br.com.winter.CreditValidatorService.infra.clients;

import br.com.winter.CreditValidatorService.entity.Card;
import br.com.winter.CreditValidatorService.entity.ClientCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CardService", path = "/cards")
public interface ICardResourceClient {

    @GetMapping("/cpf/{cpf}")
    ResponseEntity<List<ClientCard>> getCardsByClientCpf(@PathVariable String cpf);

    @GetMapping("income/{income}")
    ResponseEntity<List<Card>> getCardByIncomeLessThan(@PathVariable Long income);
}
