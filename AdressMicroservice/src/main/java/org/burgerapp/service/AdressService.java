package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.AdressSaveRequestDTO;
import org.burgerapp.entity.Adress;
import org.burgerapp.exception.AdressServiceException;
import org.burgerapp.exception.ErrorType;
import org.burgerapp.repository.AdressRepository;
import org.burgerapp.utility.JwtTokenManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdressService {
    private final AdressRepository adressRepository;
    private final JwtTokenManager jwtTokenManager;
    private final RabbitTemplate rabbitTemplate;
    private final String directExchangeAdress = "directExchangeAdress";
    private final String keyGetUserIdForAdress = "keyGetUserIdForAdress";




    public String adressSave(AdressSaveRequestDTO adressSaveRequestDTO) {
        Long authId = jwtTokenManager.getAuthIdFromToken(adressSaveRequestDTO.getToken()).orElseThrow(() -> new AdressServiceException(ErrorType.TOKEN_VERIFY_FAILED));
        String userId = convertSendAndReceiveUserId(authId);
        System.out.println(userId);
        adressRepository.save(Adress.builder().userId(userId).city(adressSaveRequestDTO.getCity()).no(adressSaveRequestDTO.getNo()).name(adressSaveRequestDTO.getName()).street(adressSaveRequestDTO.getStreet()).build());
        return "adress Saved";


        //apigateway security?
    }
    private String convertSendAndReceiveUserId(Long authId) {
        return (String) rabbitTemplate.convertSendAndReceive(directExchangeAdress, keyGetUserIdForAdress, authId);
    }

    @RabbitListener(queues = "queueAdress")
    private Adress convertSendAndReceiveAdress(String userId) {
        return adressRepository.findByUserId(userId).orElseThrow(() -> new AdressServiceException(ErrorType.ADRESS_NOT_FOUND));
    }


}
