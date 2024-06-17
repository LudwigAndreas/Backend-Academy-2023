package ru.edu.homework.springcrud.exception;

public class InitialValueException extends RuntimeException {
    public InitialValueException() {
        super();
    }

    public InitialValueException(String message) {
        super(message);
    }

    public InitialValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
