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
    private final String directExchangeUser = "directExchangeUser";
    private final String queueSaveCart = "queueSaveCart";
    private final String keySaveCart = "keySaveCart";

    @Bean
    public DirectExchange directExchangeUser() {
        return new DirectExchange(directExchangeUser);
    }
    @Bean
    public Queue queueSaveCart() {
        return new Queue(queueSaveCart);
    }
    @Bean
    public Binding bindingSaveCart(Queue queueSaveCart, DirectExchange directExchangeUser) {
        return BindingBuilder.bind(queueSaveCart).to(directExchangeUser).with(keySaveCart);
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
