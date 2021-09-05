package com.csmates.wp.exception;

public class WebAppException extends RuntimeException {
    public WebAppException() {
    }

    public WebAppException(String message) {
        super(message);
    }

    public WebAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebAppException(Throwable cause) {
        super(cause);
    }

    public WebAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
