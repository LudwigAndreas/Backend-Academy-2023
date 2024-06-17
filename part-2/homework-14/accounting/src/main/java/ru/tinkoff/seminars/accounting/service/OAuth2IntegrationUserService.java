package ru.tinkoff.seminars.accounting.service;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;

import java.util.Optional;
import java.util.UUID;

public interface OAuth2IntegrationUserService {

    Optional<UserDto> findByLogin(String login);

    Optional<UserDto> findById(UUID userId);

    UserDto addUser(UserRegistrationDto user);

    Optional<UserDto> updateUser(Jwt jwt, UUID userId, UserDto user);

    Optional<UserDto> patchUser(Jwt jwt, UUID userId, UserDto user);

    Optional<UserDto> patchUser(Jwt jwt, UUID userId, JsonPatch jsonPatch);

    void deleteUser(Jwt jwt, UUID userId);

    void sendVerificationEmail(Jwt jwt);

    void sendPasswordResetEmail(Jwt jwt);

}
