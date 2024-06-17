package ru.tinkoff.seminars.accounting.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;

import java.util.List;

public interface KeycloakService {

    void addUser(UserRegistrationDto user);

    List<UserRepresentation> getUser(String username);

    void updateUser(Jwt jwt, UserDto user);

    void deleteUser(Jwt jwt);

    void assignRealmRole(String id, String role);

    void assignClientRole(String id, String role, String clientId);

    void unassignRealmRole(String id, String role);

    void unassignClientRole(String id, String role, String clientId);
/*

    void addGroup(String id, String group);

    void deleteGroup(String id, String group);

    void addGroupRole(String group, String role);

    void deleteGroupRole(String group, String role);
*/

    void sendEmailVerification(Jwt jwt);

    void sendPasswordReset(Jwt jwt);

}
