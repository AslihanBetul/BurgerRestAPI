package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.entity.Cart;
import org.burgerapp.entity.Product;
import org.burgerapp.exception.CartServiceException;
import org.burgerapp.rabitmq.model.CustomProductModel;
import org.burgerapp.repository.CartRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.burgerapp.exception.ErrorType.CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void saveCart(String userId){
        Cart cart = Cart.builder().userId(userId).build();
        cartRepository.save(cart);
    }
    @RabbitListener(queues = "queueSaveCart")
    public void receiveUserId(String userId){
        saveCart(userId);
    }


    @RabbitListener(queues = "queueSendProduct")
    public void receiveCustomProductModel(CustomProductModel customProductModel){
        addProductToCart(customProductModel);
    }

    public void addProductToCart(CustomProductModel customProductModel){
        Cart cart = cartRepository.findByUserId(customProductModel.getUserId()).orElseThrow(() -> new CartServiceException(CART_NOT_FOUND));
        cart.getProducts().add(customProductModel.getProduct());
        cart.setTotalPrice(calculateCartTotalAmount(cart));
        cartRepository.save(cart);
    }
    private BigDecimal calculateCartTotalAmount(Cart cart){
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(Product product : cart.getProducts()){
            totalAmount = totalAmount.add(product.getPrice());
        }
        return totalAmount;
    }
}
