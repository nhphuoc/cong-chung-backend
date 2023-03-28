package vn.com.panda.learncardriving.controller.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(String message) {
        this(message, null);
    }
}
