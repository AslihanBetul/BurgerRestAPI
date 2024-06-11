package org.burgerapp.mapper;


import org.burgerapp.dto.requestDto.AuthRegisterDTO;
import org.burgerapp.entity.Auth;
import org.burgerapp.exception.AuthServiceException;
import org.burgerapp.rabitmq.model.AuthActivationModel;
import org.burgerapp.rabitmq.model.AuthhorizeSaveModel;
import org.burgerapp.rabitmq.model.UserSaveModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.burgerapp.exception.ErrorType.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth authRegisterDTOToAuth(AuthRegisterDTO authRegisterDTO);

    @Mapping(source = "id",target = "authId")
    UserSaveModel authToUserSaveModel(Auth auth);

    AuthActivationModel authToAuthActivationModel(Auth auth);

    @Mapping(source = "id",target = "authId")
    AuthhorizeSaveModel authToAuthhorizeSaveModel(Auth auth);

}
