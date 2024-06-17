package ru.tinkoff.seminars.accounting.exception;

public class OAuth2IntegrationException extends RuntimeException {
    public OAuth2IntegrationException() {
        super();
    }

    public OAuth2IntegrationException(String message) {
        super(message);
    }

    public OAuth2IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
