package com.Clientin.Clientin.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Base exception class for all custom exceptions in the application
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;
    private final String details;

    protected BaseException(String message, String errorCode, HttpStatus httpStatus) {
        this(message, errorCode, httpStatus, null, null);
    }

    protected BaseException(String message, String errorCode, HttpStatus httpStatus, Throwable cause) {
        this(message, errorCode, httpStatus, null, cause);
    }

    protected BaseException(String message, String errorCode, HttpStatus httpStatus, String details, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public abstract String getEntity();
}
