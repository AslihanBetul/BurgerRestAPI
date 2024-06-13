package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.AdressSaveRequestDTO;
import org.burgerapp.entity.Adress;
import org.burgerapp.exception.AdressServiceException;
import org.burgerapp.exception.ErrorType;
import org.burgerapp.repository.AdressRepository;
import org.burgerapp.utility.JwtTokenManager;
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


    private final String queueGetUserIdForAdress = "queueGetUserIdForAdress";

    public String adressSave(AdressSaveRequestDTO adressSaveRequestDTO) {
        Long authId = jwtTokenManager.getAuthIdFromToken(adressSaveRequestDTO.getToken()).orElseThrow(() -> new AdressServiceException(ErrorType.TOKEN_VERIFY_FAILED));
        String userId = convertSendAndReceiveUserId(authId);
        adressRepository.save(Adress.builder().userId(userId).city(adressSaveRequestDTO.getCity()).no(adressSaveRequestDTO.getNo()).name(adressSaveRequestDTO.getName()).street(adressSaveRequestDTO.getStreet()).build());
        return "adress Saved";

        //TODO Order oluşturulacak (modelini order'a göndereceğiz), order userId,product, cart'ın totel price'ını carttan alacak, o user id ile adressten adresi alıp yeni bir order nesnesi oluşturup bitirecek.
        //apigateway security?
    }
    private String convertSendAndReceiveUserId(Long authId) {
        return (String) rabbitTemplate.convertSendAndReceive(directExchangeAdress, queueGetUserIdForAdress, authId);
    }
}
