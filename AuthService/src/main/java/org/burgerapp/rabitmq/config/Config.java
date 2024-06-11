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
    private final String directExchange = "directExchange";

    private final String queueUserSave = "queueUserSave";
    private final String keyUserSave = "keyUserSave";

    private final String queueUserStatusUpdate = "queueUserStatusUpdate";
    private final String keyUserStatusUpdate = "keyUserStatusUpdate";

    private final String queueActivationCode = "queueActivationCode";
    private final String keyActivationCode = "keyActivationCode";

    private final String queueAuthorizeSave = "queueAuthorizeSave";
    private final String keyAuthorizeSave = "keyAuthorizeSave";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    public Queue queueUserSave() {
        return new Queue(queueUserSave);
    }
    @Bean
    public Queue queueUserStatusUpdate() {
        return new Queue(queueUserStatusUpdate);
    }

    @Bean
    public Queue queueActivationCode() {
        return new Queue(queueActivationCode);
    }
    @Bean
    public Queue queueAuthorizeSave() {
        return new Queue(queueAuthorizeSave);
    }

    @Bean
    public Binding bindingUserSave(Queue queueUserSave, DirectExchange directExchange) {
        return BindingBuilder.bind(queueUserSave).to(directExchange).with(keyUserSave);
    }
    @Bean
    public Binding bindingUserStatusUpdate(Queue queueUserStatusUpdate, DirectExchange directExchange) {
        return BindingBuilder.bind(queueUserStatusUpdate).to(directExchange).with(keyUserStatusUpdate);
    }

    @Bean
    public Binding bindingActivationCode(Queue queueActivationCode, DirectExchange directExchange) {
        return BindingBuilder.bind(queueActivationCode).to(directExchange).with(keyActivationCode);
    }

    @Bean
    public Binding bindingAuthorizeSave(Queue queueAuthorizeSave, DirectExchange directExchange) {
        return BindingBuilder.bind(queueAuthorizeSave).to(directExchange).with(keyAuthorizeSave);
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
