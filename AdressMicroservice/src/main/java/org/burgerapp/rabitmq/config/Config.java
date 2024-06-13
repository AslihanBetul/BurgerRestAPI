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
    private final String directExchangeAdress = "directExchangeAdress";
    private final String queueGetUserIdForAdress = "queueGetUserIdForAdress";
    private final String keyGetUserIdForAdress = "keyGetUserIdForAdress";

    @Bean
    public DirectExchange directExchangeAdress(){
        return new DirectExchange(directExchangeAdress);
    }
    @Bean
    public Queue queueGetUserIdForAdress(){
        return new Queue(queueGetUserIdForAdress);
    }
    @Bean
    public Binding bindingGetUserIdForAdress(DirectExchange directExchangeAdress,Queue queueGetUserIdForAdress){
        return BindingBuilder.bind(queueGetUserIdForAdress).to(directExchangeAdress).with(keyGetUserIdForAdress);
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
