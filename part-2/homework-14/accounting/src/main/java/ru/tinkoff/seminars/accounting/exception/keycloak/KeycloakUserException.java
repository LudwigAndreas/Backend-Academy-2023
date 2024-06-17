package ru.tinkoff.seminars.accounting.exception.keycloak;

public class KeycloakUserException extends RuntimeException {
    public KeycloakUserException() {
        super();
    }

    public KeycloakUserException(String message) {
        super(message);
    }

    public KeycloakUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
