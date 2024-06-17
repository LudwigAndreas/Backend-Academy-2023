package ru.tinkoff.seminars.accounting.dto.keycloak;

import lombok.Data;

@Data
public class KeycloakUserDto {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private boolean enabled;
}
