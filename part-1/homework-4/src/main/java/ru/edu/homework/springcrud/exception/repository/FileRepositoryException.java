package ru.edu.homework.springcrud.exception.repository;

public class FileRepositoryException extends RuntimeException {

    public FileRepositoryException() {
        super();
    }

    public FileRepositoryException(String message) {
        super(message);
    }

    public FileRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
