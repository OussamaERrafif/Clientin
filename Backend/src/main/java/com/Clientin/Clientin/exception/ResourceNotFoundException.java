package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource is not found
 */
public class ResourceNotFoundException extends BaseException {
    
    private static final String ERROR_CODE = "RESOURCE_NOT_FOUND";
    private final String entity;
    private final String resourceId;

    public ResourceNotFoundException(String entity, String resourceId) {
        super(String.format("%s with ID '%s' not found", entity, resourceId), 
              ERROR_CODE, 
              HttpStatus.NOT_FOUND);
        this.entity = entity;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String entity, String resourceId, String details) {
        super(String.format("%s with ID '%s' not found", entity, resourceId), 
              ERROR_CODE, 
              HttpStatus.NOT_FOUND, 
              details, 
              null);
        this.entity = entity;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String entity, String field, String value) {
        super(String.format("%s with %s '%s' not found", entity, field, value), 
              ERROR_CODE, 
              HttpStatus.NOT_FOUND);
        this.entity = entity;
        this.resourceId = value;
    }

    @Override
    public String getEntity() {
        return entity;
    }

    public String getResourceId() {
        return resourceId;
    }
}
