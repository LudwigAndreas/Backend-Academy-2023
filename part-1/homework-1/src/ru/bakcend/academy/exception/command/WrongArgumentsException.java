package ru.bakcend.academy.exception.command;

public class WrongArgumentsException extends CommandException {
    public WrongArgumentsException() {
        super();
    }

    public WrongArgumentsException(String message) {
        super(message);
    }

    public WrongArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}
