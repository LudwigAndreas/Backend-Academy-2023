package ru.tinkoff.seminars.accounting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;
import ru.tinkoff.seminars.accounting.model.UserModel;

@Mapper
public interface UserModelDtoMapper {

    @Mapping(target = "dateOfBirth", source = "birthDate")
    UserDto toDto(UserModel userModel);

    @Mapping(target = "birthDate", source = "dateOfBirth")
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "lastLoginDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    UserModel toModel(UserDto user);

    @Mapping(target = "birthDate", source = "dateOfBirth")
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "lastLoginDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    UserModel toModel(UserRegistrationDto user);

}
