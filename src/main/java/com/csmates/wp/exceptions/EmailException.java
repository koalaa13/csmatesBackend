package com.csmates.wp.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Use this exception when something wrong with email.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid email")
@NoArgsConstructor
public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }
}
