package ru.edu.homework.springcrud.exception.task;

public class NotCompletedException extends RuntimeException {
    public NotCompletedException() {
        super();
    }

    public NotCompletedException(String message) {
        super(message);
    }

    public NotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
