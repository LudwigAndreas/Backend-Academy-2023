package ru.tinkoff.seminars.accounting.exception;

public class MalformedJsonException extends RuntimeException {
    public MalformedJsonException() {
        super();
    }

    public MalformedJsonException(String message) {
        super(message);
    }

    public MalformedJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
