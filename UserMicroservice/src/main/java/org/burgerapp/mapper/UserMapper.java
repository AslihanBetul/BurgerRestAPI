package org.burgerapp.mapper;



import org.burgerapp.entity.User;
import org.burgerapp.rabitmq.model.UserSaveModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "userStatus",source = "authStatus")
    User userSaveModelToUser(UserSaveModel userSaveModel);


}
