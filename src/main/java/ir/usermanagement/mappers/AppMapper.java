package ir.usermanagement.mappers;

import ir.usermanagement.models.repositories.entities.AppMessage;
import ir.usermanagement.models.repositories.entities.AppUser;
import ir.usermanagement.models.services.dtoes.AppUserDTO;
import ir.usermanagement.models.services.dtoes.ChangeAppUserPasswordRequestDTO;
import ir.usermanagement.models.services.dtoes.CreateAppUserRequestDTO;
import ir.usermanagement.models.services.dtoes.UpdateAppUserRequestDTO;
import ir.usermanagement.rest.models.*;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(config = AppMapperConfig.class)
public interface AppMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    AppUser toCreateAppUser(CreateAppUserRequestDTO dto, PasswordEncoder passwordEncoder);

    AppUserDTO toAppUserDTO(AppUser appUser);

    @Mapping(target = "response", source = "username")
    @BeanMapping(ignoreByDefault = true)
    AppBaseResponse<String> toCreateUserResponse(AppUserDTO appUserDTO);

    @Mapping(target = "response.username", source = "username")
    @Mapping(target = "response.firstName", source = "firstName")
    @Mapping(target = "response.lastName", source = "lastName")
    @BeanMapping(ignoreByDefault = true)
    GetUserResponse toGetUserResponse(AppUserDTO appUserDTO);

    UpdateAppUserRequestDTO toUpdateAppUserRequestDTO(String username, UpdateUserRequest request);

    @Mapping(target = "code", source = "appMessage.code")
    @Mapping(target = "description", source = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "extraInfo", source = "detail", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @BeanMapping(ignoreByDefault = true)
    AppMessageResponseObject toAppMessageResponseObject(AppMessage appMessage, String description, String detail);

    @Mapping(target = "messages", source = "messages")
    @BeanMapping(ignoreByDefault = true)
    AppBaseResponse<String> toBadRequestResponse(List<AppMessageResponseObject> messages, String temp);

    @Mapping(target = "firstName", source = "firstName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lastName", source = "lastName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @BeanMapping(ignoreByDefault = true)
    AppUser toUpdateUser(@MappingTarget AppUser updatedAppUser, UpdateAppUserRequestDTO updateInfo);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getNewPassword()))")
    AppUser toChangeUserPassword(@MappingTarget AppUser appUser, ChangeAppUserPasswordRequestDTO dto, PasswordEncoder passwordEncoder);

    @Mapping(target = "response", source = "appUserDTOs")
    @BeanMapping(ignoreByDefault = true)
    ListUserResponse toListUserResponse(Page<AppUserDTO> appUserDTOs);

    CreateAppUserRequestDTO toCreateAppUserRequestDTO(CreateUserRequest request);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "newPassword", source = "dto.newPassword")
    @Mapping(target = "oldPassword", source = "dto.oldPassword")
    @Mapping(target = "confirmPassword", source = "dto.confirmPassword")
    ChangeAppUserPasswordRequestDTO toChangeAppUserPasswordRequestDTO(String username, ChangeUserPasswordRequest dto);
}
