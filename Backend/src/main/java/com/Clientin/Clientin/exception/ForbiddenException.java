package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when user doesn't have sufficient permissions
 */
public class ForbiddenException extends BaseException {
    
    private static final String ERROR_CODE = "FORBIDDEN";
    private final String userId;
    private final String requiredRole;
    private final String userRole;

    public ForbiddenException(String message) {
        super(message, ERROR_CODE, HttpStatus.FORBIDDEN);
        this.userId = null;
        this.requiredRole = null;
        this.userRole = null;
    }

    public ForbiddenException(String userId, String userRole, String requiredRole) {
        super(String.format("User '%s' with role '%s' does not have required role '%s'", userId, userRole, requiredRole), 
              ERROR_CODE, 
              HttpStatus.FORBIDDEN);
        this.userId = userId;
        this.requiredRole = requiredRole;
        this.userRole = userRole;
    }

    public ForbiddenException(String userId, String userRole, String requiredRole, String details) {
        super(String.format("User '%s' with role '%s' does not have required role '%s'", userId, userRole, requiredRole), 
              ERROR_CODE, 
              HttpStatus.FORBIDDEN, 
              details, 
              null);
        this.userId = userId;
        this.requiredRole = requiredRole;
        this.userRole = userRole;
    }

    @Override
    public String getEntity() {
        return "Permission";
    }

    public String getUserId() {
        return userId;
    }

    public String getRequiredRole() {
        return requiredRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
