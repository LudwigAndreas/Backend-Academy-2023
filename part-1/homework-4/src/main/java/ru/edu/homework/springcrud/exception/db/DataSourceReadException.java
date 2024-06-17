package ru.edu.homework.springcrud.exception.db;

public class DataSourceReadException extends Exception {
    public DataSourceReadException() {
        super();
    }

    public DataSourceReadException(String message) {
        super(message);
    }

    public DataSourceReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
