package ru.tinkoff.seminars.accounting.service.impl;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.tinkoff.seminars.accounting.auth.keycloak.UserCredentialsProvider;
import ru.tinkoff.seminars.accounting.dto.user.UserDto;
import ru.tinkoff.seminars.accounting.dto.user.UserRegistrationDto;
import ru.tinkoff.seminars.accounting.exception.AlreadyExistsException;
import ru.tinkoff.seminars.accounting.exception.keycloak.KeycloakUserException;
import ru.tinkoff.seminars.accounting.service.KeycloakService;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final Logger log = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    private final String DEFAULT_REALM_ROLE = "user";

    private final String DEFAULT_CLIENT_ROLE = "client_user";

    public KeycloakServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public void addUser(UserRegistrationDto user) {
        UserRepresentation userRepresentation = createUserRepresentation(user);

        UsersResource usersResource = getUsersResource();
        try {
            Response response = usersResource.create(userRepresentation);
            if (response.getStatus() != 201) {
                log.error("Failed to create user");
                throw new KeycloakUserException("Failed to create user");
            }
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRealmRole(userId, DEFAULT_REALM_ROLE);
            assignClientRole(userId, DEFAULT_CLIENT_ROLE, clientId);
        } catch (Exception e) {
            log.error("Failed to create user", e);
            throw new AlreadyExistsException("Failed to create user", e);
        }
    }

    @Override
    public List<UserRepresentation> getUser(String username) {
        UsersResource usersResource = getUsersResource();
        try {
            return usersResource.search(username, 0, 1);
        } catch (Exception e) {
            log.error("Failed to get user", e);
            throw new KeycloakUserException("Failed to get user", e);
        }
    }

    @Override
    public void updateUser(Jwt jwt, UserDto user) {
        String claimsUserId = getClaimsUserId(jwt);
        UserRepresentation userRepresentation = createUserRepresentation(user);

        UsersResource usersResource = getUsersResource();
        try {
            usersResource.get(claimsUserId).update(userRepresentation);
        } catch (Exception e) {
            log.error("Failed to update user", e);
            throw new KeycloakUserException("Failed to update user", e);
        }
    }

    @Override
    public void deleteUser(Jwt jwt) {
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(getClaimsUserId(jwt));
        try {
            userResource.remove();
        } catch (Exception e) {
            log.error("Failed to delete user", e);
            throw new KeycloakUserException("Failed to delete user", e);
        }
    }

    @Override
    public void assignRealmRole(String id, String role) {
        RoleRepresentation roleRepresentation = getRealmRoleRepresentation(role);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(id);
        try {
//            log.debug(userResource.roles().realmLevel().listAll().toString());
            userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            log.error("Failed to add role", e);
            throw new KeycloakUserException("Failed to assign realm role " + role + " to user with id " + id, e);
        }
    }

    @Override
    public void assignClientRole(String id, String role, String clientId) {
//        TODO: add check if role exists
        RoleRepresentation roleRepresentation = keycloak.realm(realm).clients().get(getClientRepresentation(clientId).getId()).roles().get(role).toRepresentation();
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(id);

        try {
            userResource.roles().clientLevel(getClientRepresentation(clientId).getId()).add(Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            log.error("Failed to add role", e);
            throw new KeycloakUserException("Failed to assign client " + clientId + " role " + role + "to user with id " + id, e);
        }
    }

    @Override
    public void unassignRealmRole(String id, String role) {
        RoleRepresentation roleRepresentation = getRealmRoleRepresentation(role);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(id);
        try {
            userResource.roles().realmLevel().remove(Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            log.error("Failed to delete role", e);
            throw new KeycloakUserException("Failed to delete role", e);
        }
    }

    @Override
    public void unassignClientRole(String id, String role, String clientId) {
        RoleRepresentation roleRepresentation = getClientRoleRepresentation(role, clientId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(id);
        try {
            userResource.roles().clientLevel(clientId).remove(Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            log.error("Failed to delete role", e);
            throw new KeycloakUserException("Failed to delete role", e);
        }
    }

    @Override
    public void sendEmailVerification(Jwt jwt) {
        UsersResource usersResource = getUsersResource();
        try {
            usersResource.get(getClaimsUserId(jwt)).executeActionsEmail(Collections.singletonList("VERIFY_EMAIL"));
        } catch (Exception e) {
            log.error("Failed to send email verification", e);
            throw new KeycloakUserException("Failed to send email verification", e);
        }
    }

    @Override
    public void sendPasswordReset(Jwt jwt) {
        UsersResource usersResource = getUsersResource();
        try {
            usersResource.get(getClaimsUserId(jwt)).executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
        } catch (Exception e) {
            log.error("Failed to send password reset", e);
            throw new KeycloakUserException("Failed to send password reset", e);
        }
    }

    private UserRepresentation createUserRepresentation(UserRegistrationDto user) {
        CredentialRepresentation credential = UserCredentialsProvider.createPasswordCredentials(user.getPassword());
        UserRepresentation userRepresentation = createUserRepresentation((UserDto) user);
        userRepresentation.setCredentials(Collections.singletonList(credential));
        userRepresentation.setAttributes(Collections.singletonMap("user_id", Collections.singletonList(user.getId().toString())));
        return userRepresentation;
    }

    private UserRepresentation createUserRepresentation(UserDto user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(user.getId().toString());
        userRepresentation.setUsername(user.getLogin());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }


//    TODO: replace with Optional<RoleRepresentation>. Can be null if role not found
    private RoleRepresentation getRealmRoleRepresentation(String role) {
        return keycloak.realm(realm).roles().get(role).toRepresentation();
    }

//    TODO: replace with Optional<RoleRepresentation>. Can be null if role not found
    private RoleRepresentation getClientRoleRepresentation(String role, String clientId) {
        return keycloak.realm(realm).clients().get(clientId).roles().get(role).toRepresentation();
    }

//    TODO: replace with Optional<ClientRepresentation>. Can be null if client not found
    private ClientRepresentation getClientRepresentation(String clientId) {
        return keycloak.realm(realm).clients().findByClientId(clientId).get(0);
    }

//    TODO: replace with Optional<ClientRepresentation>. Can be null if client not found
    private ClientRepresentation getCurrenClientRepresentation() {
        return keycloak.realm(realm).clients().findByClientId(clientId).get(0);
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private String getClaimsUserId(Jwt jwt) {
        return jwt.getSubject();
    }
}
