package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when database operations fail
 */
public class DatabaseException extends BaseException {
    
    private static final String ERROR_CODE = "DATABASE_ERROR";
    private final String entity;
    private final String operation;

    public DatabaseException(String entity, String operation, String message) {
        super(String.format("Database error for %s.%s: %s", entity, operation, message), 
              ERROR_CODE, 
              HttpStatus.INTERNAL_SERVER_ERROR);
        this.entity = entity;
        this.operation = operation;
    }

    public DatabaseException(String entity, String operation, String message, Throwable cause) {
        super(String.format("Database error for %s.%s: %s", entity, operation, message), 
              ERROR_CODE, 
              HttpStatus.INTERNAL_SERVER_ERROR, 
              cause);
        this.entity = entity;
        this.operation = operation;
    }

    public DatabaseException(String entity, String operation, String message, String details, Throwable cause) {
        super(String.format("Database error for %s.%s: %s", entity, operation, message), 
              ERROR_CODE, 
              HttpStatus.INTERNAL_SERVER_ERROR, 
              details, 
              cause);
        this.entity = entity;
        this.operation = operation;
    }

    @Override
    public String getEntity() {
        return entity;
    }

    public String getOperation() {
        return operation;
    }
}
