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
    private final String direcxtExchangeCart = "directExchangeCart";


    private final String queueUserIdAndBalance = "queueUserIdAndBalance";
    private final String keyUserIdAndBalance = "keyUserIdAndBalance";

    private final String queueUpdateUserBalance = "queueUpdateUserBalance";
    private final String keyUpdateUserBalance = "keyUpdateUserBalance";

    private final String queueOrderSave = "queueOrderSave";
    private final String keyOrderSave = "keyOrderSave";




    @Bean
    public DirectExchange directExchangeCart(){
        return new DirectExchange(direcxtExchangeCart);
    }

    @Bean
    public Queue queueUserIdAndBalance() {
        return new Queue(queueUserIdAndBalance);
    }
    @Bean
    public Binding bindingUserIdAndBalance(Queue queueUserIdAndBalance, DirectExchange directExchangeCart) {
        return BindingBuilder.bind(queueUserIdAndBalance).to(directExchangeCart).with(keyUserIdAndBalance);
    }

    @Bean
    public Queue queueUpdateUserBalance() {
        return new Queue(queueUpdateUserBalance);
    }
    @Bean
    public Binding bindingUpdateUserBalance(Queue queueUpdateUserBalance, DirectExchange directExchangeCart) {
        return BindingBuilder.bind(queueUpdateUserBalance).to(directExchangeCart).with(keyUpdateUserBalance);
    }

    @Bean
    public Queue queueOrderSave() {
        return new Queue(queueOrderSave);
    }
    @Bean
    public Binding bindingOrderSave(Queue queueOrderSave, DirectExchange directExchangeCart) {
        return BindingBuilder.bind(queueOrderSave).to(directExchangeCart).with(keyOrderSave);
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
