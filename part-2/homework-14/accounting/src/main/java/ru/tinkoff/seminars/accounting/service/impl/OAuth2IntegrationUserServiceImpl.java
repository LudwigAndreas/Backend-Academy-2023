package ru.tinkoff.seminars.accounting.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;
import ru.tinkoff.seminars.accounting.exception.ActionForbiddenException;
import ru.tinkoff.seminars.accounting.exception.MalformedJsonException;
import ru.tinkoff.seminars.accounting.exception.OAuth2IntegrationException;
import ru.tinkoff.seminars.accounting.exception.keycloak.KeycloakUserException;
import ru.tinkoff.seminars.accounting.service.KeycloakService;
import ru.tinkoff.seminars.accounting.service.OAuth2IntegrationUserService;
import ru.tinkoff.seminars.accounting.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
public class OAuth2IntegrationUserServiceImpl implements OAuth2IntegrationUserService {

    private final UserService usersService;

    private final KeycloakService keycloakService;

    public OAuth2IntegrationUserServiceImpl(UserService usersService, KeycloakService keycloakService) {
        this.usersService = usersService;
        this.keycloakService = keycloakService;
    }


    @Override
    public Optional<UserDto> findByLogin(String login) {
        return usersService.findByLogin(login);
    }

    @Override
    public Optional<UserDto> findById(UUID userId) {
        return usersService.findById(userId);
    }

    @Override
    public UserDto addUser(UserRegistrationDto userRegistrationDto) {
        try {
            UserDto userDto = usersService.save(userRegistrationDto);
            userRegistrationDto.setId(userDto.getId());
            keycloakService.addUser(userRegistrationDto);
            return userDto;
        } catch (KeycloakUserException e) {
            throw new OAuth2IntegrationException("Failed to create user", e);
        }
    }

    @Override
    public Optional<UserDto> updateUser(Jwt jwt, UUID userId, UserDto userUpdate) {
        Optional<UserDto> user;
        try {
            user = usersService.update(userId, userUpdate);
        } catch (JsonMappingException e) {
            throw new MalformedJsonException("Failed to update user", e);
        }
        keycloakService.updateUser(jwt, userUpdate);
        return user;
    }

    @Override
    public Optional<UserDto> patchUser(Jwt jwt, UUID userId, UserDto userPatch) {
        Optional<UserDto> user;
        try {
            user = usersService.patch(userId, userPatch);
        } catch (JsonMappingException e) {
            throw new MalformedJsonException("Failed to patch user", e);
        }
//        TODO: configure KeycloakService to accept UserDto as a patch
//        keycloakService.updateUser(jwt, userPatch);
        return user;
    }

    @Override
    public Optional<UserDto> patchUser(Jwt jwt, UUID userId, JsonPatch jsonPatch) {
        Optional<UserDto> user;
        try {
            user = usersService.patch(userId, jsonPatch);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new MalformedJsonException("Failed to patch user", e);
        }
//        TODO: configure KeycloakService to accept JsonPatch
//        keycloakService.updateUser(jwt, jsonPatch);
        return user;
    }

    @Override
    public void deleteUser(Jwt jwt, UUID userId) {
        try {
            if (jwt == null) {
                throw new OAuth2IntegrationException("Failed to delete user: no JWT provided");
            } else if (jwt.getClaim("user_id") == null) {
                throw new OAuth2IntegrationException("Failed to delete user: no user ID provided");
            } else if (!jwt.getClaim("user_id").equals(userId.toString())) {
                throw new ActionForbiddenException("Failed to delete user: user ID does not match JWT");
            }
            usersService.delete(UUID.fromString(jwt.getClaim("user_id")));
            keycloakService.deleteUser(jwt);
        } catch (KeycloakUserException e) {
            throw new OAuth2IntegrationException("Failed to delete user", e);
        }
    }

    @Override
    public void sendVerificationEmail(Jwt jwt) {
        keycloakService.sendEmailVerification(jwt);
    }

    @Override
    public void sendPasswordResetEmail(Jwt jwt) {
        keycloakService.sendPasswordReset(jwt);
    }
}
