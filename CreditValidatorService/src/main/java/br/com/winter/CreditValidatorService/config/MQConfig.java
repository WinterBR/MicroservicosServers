package br.com.winter.CreditValidatorService.config;

import jakarta.validation.Valid;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.cards-emission}")
    private String queueCardEmission;

    @Bean
    public Queue cardEmissionQueue() {
        return new Queue(queueCardEmission, true);
    }
}
