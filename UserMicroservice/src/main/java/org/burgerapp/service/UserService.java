package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.entity.User;
import org.burgerapp.entity.enums.UserStatus;
import org.burgerapp.exception.UserServiceException;
import org.burgerapp.mapper.UserMapper;
import org.burgerapp.rabitmq.model.UserIdAndBalanceModel;
import org.burgerapp.rabitmq.model.UserSaveModel;
import org.burgerapp.rabitmq.model.UserStatusUpdateModel;
import org.burgerapp.repository.UserRepository;
import org.burgerapp.utility.JwtTokenManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.burgerapp.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final JwtTokenManager jwtTokenManager;
    private final String directExchangeUser = "directExchangeUser";
    private final String keySaveCart = "keySaveCart";

    public void  saveUser(UserSaveModel userSaveModel){
        User savedUser = userRepository.save(UserMapper.INSTANCE.userSaveModelToUser(userSaveModel));
        convertAndSendUserId(savedUser.getId());

    }
    private void convertAndSendUserId(String userId){
        rabbitTemplate.convertAndSend(directExchangeUser, keySaveCart, userId);
    }
    @RabbitListener(queues = "queueUserSave")
    public void receiveUserSaveRequestModel(UserSaveModel userSaveModel) {
        saveUser(userSaveModel);
    }

    public void userStatusupdate(Long autId){
        User user = isUserExist(autId);
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        if(!user.getUserStatus().equals(UserStatus.ACTIVE)){
            throw new UserServiceException(USER_SERVICE_UPDATE_STATUS_FAILED);
        }
    }
    @RabbitListener(queues = "queueUserStatusUpdate")
    public void receiveSocialMediaAuth(UserStatusUpdateModel userStatusUpdateModel) {
        userStatusupdate(userStatusUpdateModel.getAuthId());
    }

    private User isUserExist(Long authId){
        return userRepository.findByAuthId(authId).orElseThrow(() -> new UserServiceException(USER_NOT_FOUND));
    }

    @RabbitListener(queues = "queueGetUserId")
    public String receiveAndSendUserId(Long authId) {
        User user = userRepository.findByAuthId(authId).orElseThrow(() -> new UserServiceException(USER_NOT_FOUND));
        return user.getId();
    }

    @RabbitListener(queues = "queueUserIdAndBalance")
    public UserIdAndBalanceModel  receiveAndSendUserIdAndBalance(Long authId) {
        User user = userRepository.findByAuthId(authId).orElseThrow(() -> new UserServiceException(USER_NOT_FOUND));
        return UserIdAndBalanceModel.builder().userId(user.getId()).balance(user.getBalance()).build();
    }

    public String updateBalance(String token, BigDecimal balance) {
        Long authId = jwtTokenManager.getAuthIdFromToken(token).orElseThrow(() -> new UserServiceException(TOKEN_VERIFY_FAILED));
        User user = userRepository.findByAuthId(authId).orElseThrow(() -> new UserServiceException(USER_NOT_FOUND));
        user.setBalance(balance);
        userRepository.save(user);
        return "Yükleme Tamamlandı";
    }


    @RabbitListener(queues = "queueUpdateUserBalance")
    public void convertAndReceiveBalance(UserIdAndBalanceModel userIdAndBalanceModel){
        User user = userRepository.findById(userIdAndBalanceModel.getUserId()).orElseThrow(() -> new UserServiceException(USER_NOT_FOUND));
        user.setBalance(userIdAndBalanceModel.getBalance());
        userRepository.save(user);
    }


}
