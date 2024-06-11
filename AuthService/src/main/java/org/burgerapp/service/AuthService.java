package org.burgerapp.service;


import lombok.RequiredArgsConstructor;

import org.burgerapp.dto.requestDto.AccountActivationRequestDTO;
import org.burgerapp.dto.requestDto.AuthRegisterDTO;
import org.burgerapp.entity.Auth;
import org.burgerapp.entity.enums.AuthStatus;
import org.burgerapp.exception.AuthServiceException;
import org.burgerapp.exception.ErrorType;
import org.burgerapp.mapper.AuthMapper;
import org.burgerapp.rabitmq.model.AuthActivationModel;
import org.burgerapp.rabitmq.model.AuthhorizeSaveModel;
import org.burgerapp.rabitmq.model.UserSaveModel;
import org.burgerapp.rabitmq.model.UserStatusUpdateModel;
import org.burgerapp.repository.AuthRepository;
import org.burgerapp.utility.CodeGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.burgerapp.constant.messages.SuccessMessages.*;
import static org.burgerapp.exception.ErrorType.*;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CodeGenerator codeGenerator;
    private final RabbitTemplate rabbitTemplate;
    private final String directExchange = "directExchange";


    public String register(AuthRegisterDTO authRegisterDTO) {
        confirmPassword(authRegisterDTO.getPassword(),authRegisterDTO.getConfirmPassword());
        checkUsernameExist(authRegisterDTO.getUsername());


        Auth auth = AuthMapper.INSTANCE.authRegisterDTOToAuth(authRegisterDTO);
        auth.setActivationCode(codeGenerator.codeGenerator());

        Auth savedAuth = authRepository.save(auth);
        if(savedAuth.getId() ==null){
            throw new AuthServiceException(ACCOUNT_CREATION_FAILED);
        }
        convertAndSendUserSaveModel(AuthMapper.INSTANCE.authToUserSaveModel(savedAuth));
        convertAndSendUserActivationCode(AuthMapper.INSTANCE.authToAuthActivationModel(savedAuth));
        converAndSendAuthorizeSave(AuthMapper.INSTANCE.authToAuthhorizeSaveModel(savedAuth));
        return ACTIVATION_CODE_SENDED;
    }
    private void convertAndSendUserSaveModel(UserSaveModel userSaveModel){
        String keyUserSave = "keyUserSave";
        rabbitTemplate.convertAndSend(directExchange, keyUserSave,userSaveModel);
    }
    private void convertAndSendUserActivationCode(AuthActivationModel authActivationModel){
        String keyActivationCode = "keyActivationCode";
        rabbitTemplate.convertAndSend(directExchange, keyActivationCode,authActivationModel);
    }
    private void converAndSendAuthorizeSave(AuthhorizeSaveModel authhorizeSaveModel){
        String keyAuthorizeSave = "keyAuthorizeSave";
        rabbitTemplate.convertAndSend(directExchange, keyAuthorizeSave,authhorizeSaveModel);
    }

    private void confirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new AuthServiceException(PASSWORD_MISMATCH);
        }
    }
    private void checkUsernameExist(String username) {
        if(authRepository.existsByUsername(username)) {
            throw new AuthServiceException(USERNAME_ALREADY_TAKEN);
        }
    }

    public String verifyAccount(AccountActivationRequestDTO accountActivationRequestDTO) {
        Auth auth = checkAuthByUsernameAndPassword(accountActivationRequestDTO.getUsername(), accountActivationRequestDTO.getPassword());
        isAccountActivatable(auth);
        isActivationCodeBelongToAccount(auth,accountActivationRequestDTO);
        auth.setAuthStatus(AuthStatus.ACTIVE);
        authRepository.save(auth);


        convertAndSendUserStatusUpdateModel(UserStatusUpdateModel.builder().authId(auth.getId()).build());

        return ACTIVATION_SUCCESFULL;
    }
    private void convertAndSendUserStatusUpdateModel(UserStatusUpdateModel userStatusUpdateModel){
        String keyUserStatusUpdate = "keyUserStatusUpdate";
        rabbitTemplate.convertAndSend(directExchange, keyUserStatusUpdate,userStatusUpdateModel);
    }
    private void isActivationCodeBelongToAccount(Auth auth,AccountActivationRequestDTO accountActivationRequestDTO) {
        if(!(auth.getActivationCode().equals(accountActivationRequestDTO.getActivationCode()))){
            throw new AuthServiceException(ACTIVATION_CODE_WRONG);
        }
    }
    private void isAccountActivatable(Auth auth) {
        if(auth.getAuthStatus().equals(AuthStatus.ACTIVE)) {
            throw new AuthServiceException(ACCOUNT_ALREADY_ACTIVATED);
        }
        if((auth.getAuthStatus().equals(AuthStatus.BANNED))){
            throw new AuthServiceException(ACCOUNT_IS_BANNED);
        }
        if((auth.getAuthStatus().equals(AuthStatus.DELETED))){
            throw new AuthServiceException(ACCOUNT_IS_DELETED);
        }

    }
    private Auth checkAuthByUsernameAndPassword(String username, String password){
        return authRepository.findOptionalByUsernameAndPassword(username, password)
                .orElseThrow(() -> new AuthServiceException(USERNAME_OR_PASSWORD_WRONG));
    }
}
