package vn.com.panda.learncardriving.controller.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message) {
        this(message, null);
    }
}
