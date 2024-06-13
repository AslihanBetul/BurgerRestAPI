package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.entity.Cart;

import org.burgerapp.entity.Product;
import org.burgerapp.exception.CartServiceException;
import org.burgerapp.rabitmq.model.CustomProductModel;
import org.burgerapp.rabitmq.model.UserIdAndBalanceModel;
import org.burgerapp.repository.CartRepository;
import org.burgerapp.utility.JwtTokenManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.burgerapp.exception.ErrorType.CART_NOT_FOUND;
import static org.burgerapp.exception.ErrorType.TOKEN_VERIFY_FAILED;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final JwtTokenManager jwtTokenManager;
    private final RabbitTemplate rabbitTemplate;
    private final String direcxtExchangeCart = "directExchangeCart";
    private final String keyUserIdAndBalance = "keyUserIdAndBalance";


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

    public String pay(String token) {
        Long authId = jwtTokenManager.getAuthIdFromToken(token).orElseThrow(() -> new CartServiceException(TOKEN_VERIFY_FAILED));
        UserIdAndBalanceModel userIdAndBalanceModel = convertSendAndRecieveUserIdAnBalance(authId);
        Cart cart = cartRepository.findByUserId(userIdAndBalanceModel.getUserId()).orElseThrow(()->new CartServiceException(CART_NOT_FOUND));
        System.out.println(userIdAndBalanceModel.getBalance());
        if((cart.getTotalPrice().compareTo(userIdAndBalanceModel.getBalance()) <= 0)){
            //TODO güncel bakiyeyi userservice'e gönder
            return "Ödeme Onaylandı";
        }
        return "Bakiye Yetersiz";
    }
    private UserIdAndBalanceModel convertSendAndRecieveUserIdAnBalance(Long authId){
        return (UserIdAndBalanceModel) rabbitTemplate.convertSendAndReceive(direcxtExchangeCart, keyUserIdAndBalance, authId);
    }

}
