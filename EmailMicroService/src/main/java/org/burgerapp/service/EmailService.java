package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.rabbitmq.model.AuthActivationModel;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;


    public void sendEmail(AuthActivationModel authActivationModel){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(authActivationModel.getEmail());
        message.setBcc("ertugrulsaliher@gmail.com");
        message.setSubject("Your Activation Code");
        message.setText("Activation Code: "+ authActivationModel.getActivationCode());
        mailSender.send(message);
    }

    @RabbitListener(queues = "queueActivationCode")
    public void activateCode(AuthActivationModel authActivationModel){
        sendEmail(authActivationModel);
    }
}
