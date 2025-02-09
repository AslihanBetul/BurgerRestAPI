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

    private final String directExchangeOrder ="directExchangeOrder";
    private final String queueAdress = "queueAdress";
    private final String keyAdress = "keyAdress";

    @Bean
    public DirectExchange directExchangeOrder(){
        return new DirectExchange(directExchangeOrder);
    }
    @Bean
    public Queue queueAdress(){
        return new Queue(queueAdress);
    }
    @Bean
    public Binding bindingAdress(DirectExchange directExchangeOrder, Queue queueAdress){
        return BindingBuilder.bind(queueAdress).to(directExchangeOrder).with(keyAdress);
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
