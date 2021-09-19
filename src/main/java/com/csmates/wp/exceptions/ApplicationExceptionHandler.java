package com.csmates.wp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

// TODO now I'm throwing IllegalStateException everywhere, should throw my own exceptions and handle them here
@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Map<String, Object>> handleException() {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 401);
        errorResponse.put("error", HttpStatus.UNAUTHORIZED.name());
        errorResponse.put("message", "Invalid email");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // TODO configure it mb
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
