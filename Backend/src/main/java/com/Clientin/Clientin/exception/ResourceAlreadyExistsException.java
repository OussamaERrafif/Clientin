package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to create a resource that already exists
 */
public class ResourceAlreadyExistsException extends BaseException {
    
    private static final String ERROR_CODE = "RESOURCE_ALREADY_EXISTS";
    private final String entity;
    private final String field;
    private final String value;

    public ResourceAlreadyExistsException(String entity, String field, String value) {
        super(String.format("%s with %s '%s' already exists", entity, field, value), 
              ERROR_CODE, 
              HttpStatus.CONFLICT);
        this.entity = entity;
        this.field = field;
        this.value = value;
    }

    public ResourceAlreadyExistsException(String entity, String field, String value, String details) {
        super(String.format("%s with %s '%s' already exists", entity, field, value), 
              ERROR_CODE, 
              HttpStatus.CONFLICT, 
              details, 
              null);
        this.entity = entity;
        this.field = field;
        this.value = value;
    }

    @Override
    public String getEntity() {
        return entity;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
