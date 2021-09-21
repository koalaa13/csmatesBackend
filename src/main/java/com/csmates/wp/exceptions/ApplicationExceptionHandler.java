package com.csmates.wp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
     * This method invoked when field with {@link javax.validation.Valid}
     * doesn't match limitations installed with annotations.
     * For example, {@link javax.validation.constraints.NotBlank}.
     *
     * @param exception thrown exception
     * @return {@link ResponseEntity} representing thrown exception like HttpResponse Body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
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
