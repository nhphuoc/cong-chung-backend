package vn.com.panda.learncardriving.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.com.panda.learncardriving.controller.exception.DuplicatedException;
import vn.com.panda.learncardriving.controller.exception.InvalidDataException;
import vn.com.panda.learncardriving.controller.exception.NotFoundException;
import vn.com.panda.learncardriving.controller.exception.UnauthorizedException;
import vn.com.panda.learncardriving.controller.exception.ValidateException;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError notFoundHandler(NotFoundException ex) {
        return ApiError.builder().message("Resource not found.").status(HttpStatus.NOT_FOUND)
                .debugMessage(ex.getLocalizedMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ApiError handleForbiddenException(UnauthorizedException ex) {
        log.info("user could not login", ex);
        return ApiError.builder().message("username or password or login code incorrect.")
                .status(HttpStatus.UNAUTHORIZED).debugMessage(ex.getLocalizedMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({InvalidDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleInvalidDataException(InvalidDataException ex) {
        log.info("could not create", ex);
        return ApiError.builder().message("Data type invalid or exist").status(HttpStatus.UNPROCESSABLE_ENTITY)
                .debugMessage(ex.getLocalizedMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({ValidateException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ApiError handleArgumentDataException(ValidateException ex) {
        return ApiError.builder().message(ex.getLocalizedMessage()).status(HttpStatus.UNPROCESSABLE_ENTITY)
                .debugMessage(ex.getLocalizedMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({DuplicatedException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiError handleDuplicatedException(DuplicatedException ex) {
        return ApiError.builder().message(ex.getMessage()).status(HttpStatus.CONFLICT)
                .debugMessage(ex.getLocalizedMessage()).build();
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .orElse(null);

        if(fieldError == null) {
            return new ResponseEntity<>(ApiError.builder().message("Validate exception").status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .debugMessage(ex.getLocalizedMessage()).build(), HttpStatus.BAD_REQUEST);
        }
        ApiError apiError = ApiError.builder()
                .message(fieldError.getField() + " " + fieldError.getDefaultMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
