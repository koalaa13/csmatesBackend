package com.csmates.wp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Use this exception when entity is expired.
 * For example {@link com.csmates.wp.domain.ConfirmationToken} is expired.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid email")
public class EntityExpiredException extends RuntimeException {
    private final String entityName;

    public EntityExpiredException(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return entityName + " is expired.";
    }
}
