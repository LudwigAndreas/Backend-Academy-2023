package ru.bakcend.academy.exception.product;

public class ProductNotUniqueException extends Exception {
    public ProductNotUniqueException() {
        super();
    }

    public ProductNotUniqueException(String message) {
        super(message);
    }

    public ProductNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
