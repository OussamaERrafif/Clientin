package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when external service communication fails
 */
public class ExternalServiceException extends BaseException {
    
    private static final String ERROR_CODE = "EXTERNAL_SERVICE_ERROR";
    private final String serviceName;
    private final String operation;
    private final Integer statusCode;

    public ExternalServiceException(String serviceName, String operation, String message) {
        super(String.format("External service '%s' error during '%s': %s", serviceName, operation, message), 
              ERROR_CODE, 
              HttpStatus.BAD_GATEWAY);
        this.serviceName = serviceName;
        this.operation = operation;
        this.statusCode = null;
    }

    public ExternalServiceException(String serviceName, String operation, String message, Integer statusCode) {
        super(String.format("External service '%s' error during '%s' (status: %d): %s", serviceName, operation, statusCode, message), 
              ERROR_CODE, 
              HttpStatus.BAD_GATEWAY);
        this.serviceName = serviceName;
        this.operation = operation;
        this.statusCode = statusCode;
    }

    public ExternalServiceException(String serviceName, String operation, String message, Throwable cause) {
        super(String.format("External service '%s' error during '%s': %s", serviceName, operation, message), 
              ERROR_CODE, 
              HttpStatus.BAD_GATEWAY, 
              cause);
        this.serviceName = serviceName;
        this.operation = operation;
        this.statusCode = null;
    }

    public ExternalServiceException(String serviceName, String operation, String message, String details, Integer statusCode, Throwable cause) {
        super(String.format("External service '%s' error during '%s' (status: %s): %s", serviceName, operation, statusCode, message), 
              ERROR_CODE, 
              HttpStatus.BAD_GATEWAY, 
              details, 
              cause);
        this.serviceName = serviceName;
        this.operation = operation;
        this.statusCode = statusCode;
    }

    @Override
    public String getEntity() {
        return "ExternalService";
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getOperation() {
        return operation;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
