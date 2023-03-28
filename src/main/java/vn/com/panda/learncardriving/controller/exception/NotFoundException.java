package vn.com.panda.learncardriving.controller.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        this(message, null);
    }
}
