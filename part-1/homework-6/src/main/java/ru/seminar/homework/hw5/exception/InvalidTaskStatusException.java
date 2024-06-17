package ru.seminar.homework.hw5.exception;

public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException() {
        super();
    }

    public InvalidTaskStatusException(String message) {
        super(message);
    }

    public InvalidTaskStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTaskStatusException(Throwable cause) {
        super(cause);
    }

    protected InvalidTaskStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
