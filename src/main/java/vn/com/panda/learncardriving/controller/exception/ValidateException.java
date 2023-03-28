package vn.com.panda.learncardriving.controller.exception;

public class ValidateException extends IllegalArgumentException {
    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
    public ValidateException(String message) {
        this(message, null);
    }
}
