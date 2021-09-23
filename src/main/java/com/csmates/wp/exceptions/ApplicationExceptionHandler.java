package com.csmates.wp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {
    // TODO move it to prop file
    private final String statusHeader = "status";
    private final String errorHeader = "errorName";
    private final String errorsInfoHeader = "errorsInfo";

    /**
     * This method invoked when {@link EmailException} has thrown.
     *
     * @param exception thrown exception
     * @return {@link ResponseEntity} representing thrown exception like HttpResponse Body
     */
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Map<String, Object>> handleEmailException(
            EmailException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        errorResponse.put(statusHeader, status.value());
        errorResponse.put(errorHeader, status.name());
        errorResponse.put(errorsInfoHeader, new HashMap<String, String>() {{
            put("email", exception.getMessage());
        }});

        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * This method invoked when {@link TransactionSystemException} has thrown.
     * {@link TransactionSystemException} thrown when JPA cannot commit transaction because of any reason.
     * In this handler method I want to process only special annotations cases.
     * For example {@link javax.validation.constraints.Size}.
     *
     * @param exception thrown exception
     * @return {@link ResponseEntity} representing thrown exception like HttpResponse Body
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            TransactionSystemException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        errorResponse.put(statusHeader, status.value());
        errorResponse.put(errorHeader, status.name());

        // Trying to find ConstraintViolationException in parents.
        Throwable cause = exception.getCause();
        while (cause != null) {
            if (cause instanceof ConstraintViolationException) {
                break;
            }
            cause = cause.getCause();
        }
        /*
        TODO assert is not ok, idk does TransactionSystemException
        TODO always have parent ConstraintViolationException that contains reasons
        TODO of transaction error
        */
        assert cause != null;
        Map<String, String> errorReasons = new HashMap<>();
        ConstraintViolationException reasons = (ConstraintViolationException) cause;
        reasons.getConstraintViolations().forEach(reason -> errorReasons.put(reason.getPropertyPath().toString(), reason.getMessage()));
        errorResponse.put(errorsInfoHeader, errorReasons);

        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * This method invoked when field with {@link javax.validation.Valid}
     * doesn't match limitations installed with annotations.
     * For example, {@link javax.validation.constraints.NotBlank}.
     *
     * @param exception thrown exception
     * @return {@link ResponseEntity} representing thrown exception like HttpResponse Body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        errorResponse.put(statusHeader, status.value());
        errorResponse.put(errorHeader, status.name());
        Map<String, String> errorsInfo = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorsInfo.put(fieldName, errorMessage);
        });
        errorResponse.put(errorsInfoHeader, errorsInfo);

        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * This method invoked when {@link ServerException} has thrown.
     *
     * @return {@link ResponseEntity} representing thrown exception like HttpResponse Body
     */
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleServerException() {
        Map<String, Object> errorResponse = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        errorResponse.put(statusHeader, status.value());
        errorResponse.put(errorHeader, status.name());
        errorResponse.put("message", "Internal server error happened. Try again later.");

        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * This method invoked when {@link EntityExpiredException} has thrown.
     *
     * @return {@link ResponseEntity} representing thrown exception like HttpResponse Body
     */
    @ExceptionHandler(EntityExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleEntityExpiredException(
            EntityExpiredException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        errorResponse.put(statusHeader, status.value());
        errorResponse.put(errorHeader, status.name());
        errorResponse.put("message", exception.getMessage());

        return new ResponseEntity<>(errorResponse, status);
    }
}
