package br.com.fiap.gs_rise.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.fiap.gs_rise.config.RabbitMQConfig.QUEUE_OBJETIVOS;

@Slf4j
@Component
public class ObjetivoConsumer {

    @RabbitListener(queues = QUEUE_OBJETIVOS)
    public void consumirMensagem(String mensagem) {
        log.info("📩 Mensagem recebida da fila: {}", mensagem);
    }
}
