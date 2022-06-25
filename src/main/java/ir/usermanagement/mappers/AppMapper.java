package ir.usermanagement.mappers;

import ir.usermanagement.models.entities.AppMessage;
import ir.usermanagement.models.entities.AppUser;
import ir.usermanagement.rest.models.*;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(config = AppMapperConfig.class)
public interface AppMapper {

    @Mapping(target = "response.username", source = "username")
    @Mapping(target = "response.firstName", source = "firstName")
    @Mapping(target = "response.lastName", source = "lastName")
    @BeanMapping(ignoreByDefault = true)
    GetUserResponse toGetUserResponse(AppUser appUser);

    @Mapping(target = "code", source = "appMessage.code")
    @Mapping(target = "description", source = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "extraInfo", source = "detail", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @BeanMapping(ignoreByDefault = true)
    AppMessageResponseObject toAppMessageResponseObject(AppMessage appMessage, String description, String detail);

    @Mapping(target = "messages", source = "messages")
    @BeanMapping(ignoreByDefault = true)
    AppBaseResponse<String> toBadRequestResponse(List<AppMessageResponseObject> messages, String temp);

    @Mapping(target = "firstName", source = "request.firstName")
    @Mapping(target = "lastName", source = "request.lastName")
    @Mapping(target = "username", source = "request.username")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @BeanMapping(ignoreByDefault = true)
    AppUser toCreateUser(CreateUserRequest request, PasswordEncoder passwordEncoder);

    @Mapping(target = "response", source = "username")
    @BeanMapping(ignoreByDefault = true)
    AppBaseResponse<String> toCreateUserResponse(AppUser appUser);

    @Mapping(target = "firstName", source = "firstName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lastName", source = "lastName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @BeanMapping(ignoreByDefault = true)
    AppUser toUpdateUser(@MappingTarget AppUser updatedAppUser, UpdateUserRequest updateInfo);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getNewPassword()))")
    AppUser toChangeUserPassword(@MappingTarget AppUser appUser, ChangeUserPasswordRequest request, PasswordEncoder passwordEncoder);

    @Mapping(target = "response", source = "username")
    @BeanMapping(ignoreByDefault = true)
    AppBaseResponse<String> toDeleteUserResponse(AppUser appUser);

    @Mapping(target = "response", source = "appUsers")
    @BeanMapping(ignoreByDefault = true)
    ListUserResponse toListUserResponse(Page<AppUser> appUsers);
}
