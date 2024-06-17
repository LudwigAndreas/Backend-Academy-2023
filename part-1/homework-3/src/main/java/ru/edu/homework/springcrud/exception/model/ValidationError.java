package ru.edu.homework.springcrud.exception.model;

import org.springframework.validation.FieldError;

public class ValidationError extends RuntimeException {

    public ValidationError() {
        super();
    }

    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(String message, Throwable cause) {
        super(message, cause);
    }

}
