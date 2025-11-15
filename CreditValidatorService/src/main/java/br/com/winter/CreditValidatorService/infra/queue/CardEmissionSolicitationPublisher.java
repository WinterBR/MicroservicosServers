package br.com.winter.CreditValidatorService.infra.queue;

import br.com.winter.CreditValidatorService.entity.CardEmissionSolicitationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardEmissionSolicitationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueCardsEmission;

    @Autowired
    public CardEmissionSolicitationPublisher(RabbitTemplate rabbitTemplate, Queue queueCardsEmission) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueCardsEmission = queueCardsEmission;

    }

    public void requestCard(CardEmissionSolicitationData data) throws JsonProcessingException {
        var json = convertIntoJson(data);
        rabbitTemplate.convertAndSend(queueCardsEmission.getName() ,json);
    }

    private String convertIntoJson(CardEmissionSolicitationData data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(data);
        return json;
    }


}
