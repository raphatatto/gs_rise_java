package br.com.fiap.gs_rise.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_OBJETIVOS = "fila_objetivos";
    public static final String EXCHANGE_OBJETIVOS = "exchange_objetivos";
    public static final String ROUTING_KEY = "objetivo.created";

    @Bean
    public Queue objetivosQueue() {
        return new Queue(QUEUE_OBJETIVOS, true);
    }

    @Bean
    public TopicExchange objetivosExchange() {
        return new TopicExchange(EXCHANGE_OBJETIVOS);
    }

    @Bean
    public Binding objetivosBinding() {
        return BindingBuilder.bind(objetivosQueue())
                .to(objetivosExchange())
                .with(ROUTING_KEY);
    }
}
