package org.burgerapp.rabitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private final String directExchangeProduct = "directExchangeProduct";

    private final String queueGetUserId = "queueGetUserId";
    private final String keyGetUserId = "keyGetUserId";

    private final String queueSendProduct = "queueSendProduct";
    private final String keySendProduct = "keySendProduct";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeProduct);
    }
    @Bean
    public Queue queueSendProduct() {
        return new Queue(queueSendProduct);
    }
    @Bean
    public Queue queueGetUserId() {
        return new Queue(queueGetUserId);
    }
    @Bean
    public Binding bindingGetUserId(Queue queueGetUserId, DirectExchange directExchange) {
        return BindingBuilder.bind(queueGetUserId).to(directExchange).with(keyGetUserId);
    }
    @Bean
    public Binding bindingSendProduct(Queue queueSendProduct, DirectExchange directExchange) {
        return BindingBuilder.bind(queueSendProduct).to(directExchange).with(keySendProduct);
    }


    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }



}
