package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.entity.User;
import org.burgerapp.entity.enums.UserStatus;
import org.burgerapp.exception.UserServiceException;
import org.burgerapp.mapper.UserMapper;
import org.burgerapp.rabitmq.model.UserSaveModel;
import org.burgerapp.rabitmq.model.UserStatusUpdateModel;
import org.burgerapp.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.burgerapp.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
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

}
