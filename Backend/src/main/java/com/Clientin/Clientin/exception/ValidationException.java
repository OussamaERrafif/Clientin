package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation fails
 */
public class ValidationException extends BaseException {
    
    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private final String entity;
    private final String field;

    public ValidationException(String message) {
        super(message, ERROR_CODE, HttpStatus.BAD_REQUEST);
        this.entity = "Unknown";
        this.field = null;
    }

    public ValidationException(String entity, String field, String message) {
        super(String.format("Validation failed for %s.%s: %s", entity, field, message), 
              ERROR_CODE, 
              HttpStatus.BAD_REQUEST);
        this.entity = entity;
        this.field = field;
    }

    public ValidationException(String entity, String field, String message, String details) {
        super(String.format("Validation failed for %s.%s: %s", entity, field, message), 
              ERROR_CODE, 
              HttpStatus.BAD_REQUEST, 
              details, 
              null);
        this.entity = entity;
        this.field = field;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, ERROR_CODE, HttpStatus.BAD_REQUEST, cause);
        this.entity = "Unknown";
        this.field = null;
    }

    @Override
    public String getEntity() {
        return entity;
    }

    public String getField() {
        return field;
    }
}
