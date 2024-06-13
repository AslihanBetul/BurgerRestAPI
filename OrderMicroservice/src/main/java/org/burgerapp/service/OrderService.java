package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.entity.Adress;
import org.burgerapp.entity.Order;
import org.burgerapp.rabitmq.model.OrderSaveModel;
import org.burgerapp.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String queueOrderSave = "queueOrderSave";
    private final String directExchangeOrder ="directExchangeOrder";
    private final String keyAdress = "keyAdress";

    public void save(OrderSaveModel orderSaveModel) {
        Order order = Order.builder().userId(orderSaveModel.getUserId()).products(orderSaveModel.getProduct()).totalPrice(orderSaveModel.getTotalPrice()).build();
        Adress adress = convertSentAndReceiveAdress(orderSaveModel.getUserId());
        order.setAdress(adress);
        orderRepository.save(order);
    }



    @RabbitListener(queues = queueOrderSave)
    private void receiveOrder(OrderSaveModel orderSaveModel) {
        save(orderSaveModel);
    }

    private Adress convertSentAndReceiveAdress(String userId){
        return (Adress) rabbitTemplate.convertSendAndReceive(directExchangeOrder, keyAdress, userId);
    }





}
