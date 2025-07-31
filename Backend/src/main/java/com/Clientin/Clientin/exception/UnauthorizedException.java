package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when user is not authorized to perform an action
 */
public class UnauthorizedException extends BaseException {
    
    private static final String ERROR_CODE = "UNAUTHORIZED";
    private final String userId;
    private final String action;
    private final String resource;

    public UnauthorizedException(String message) {
        super(message, ERROR_CODE, HttpStatus.UNAUTHORIZED);
        this.userId = null;
        this.action = null;
        this.resource = null;
    }

    public UnauthorizedException(String userId, String action, String resource) {
        super(String.format("User '%s' is not authorized to perform '%s' on '%s'", userId, action, resource), 
              ERROR_CODE, 
              HttpStatus.UNAUTHORIZED);
        this.userId = userId;
        this.action = action;
        this.resource = resource;
    }

    public UnauthorizedException(String userId, String action, String resource, String details) {
        super(String.format("User '%s' is not authorized to perform '%s' on '%s'", userId, action, resource), 
              ERROR_CODE, 
              HttpStatus.UNAUTHORIZED, 
              details, 
              null);
        this.userId = userId;
        this.action = action;
        this.resource = resource;
    }

    @Override
    public String getEntity() {
        return "Authorization";
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getResource() {
        return resource;
    }
}
