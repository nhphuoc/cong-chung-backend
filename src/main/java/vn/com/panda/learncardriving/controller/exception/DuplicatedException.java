package vn.com.panda.learncardriving.controller.exception;

public class DuplicatedException extends RuntimeException {
    public DuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedException(String message) {
        this(message, null);
    }
}
