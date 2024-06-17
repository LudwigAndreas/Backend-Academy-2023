package ru.tinkoff.seminars.accounting.exception;

public class ActionForbiddenException extends RuntimeException {
    public ActionForbiddenException() {
        super();
    }

    public ActionForbiddenException(String message) {
        super(message);
    }

    public ActionForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
