package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a business rule is violated
 */
public class BusinessRuleException extends BaseException {
    
    private static final String ERROR_CODE = "BUSINESS_RULE_VIOLATION";
    private final String entity;
    private final String rule;

    public BusinessRuleException(String entity, String rule, String message) {
        super(String.format("Business rule violation in %s: %s - %s", entity, rule, message), 
              ERROR_CODE, 
              HttpStatus.BAD_REQUEST);
        this.entity = entity;
        this.rule = rule;
    }

    public BusinessRuleException(String entity, String rule, String message, String details) {
        super(String.format("Business rule violation in %s: %s - %s", entity, rule, message), 
              ERROR_CODE, 
              HttpStatus.BAD_REQUEST, 
              details, 
              null);
        this.entity = entity;
        this.rule = rule;
    }

    public BusinessRuleException(String entity, String rule, String message, Throwable cause) {
        super(String.format("Business rule violation in %s: %s - %s", entity, rule, message), 
              ERROR_CODE, 
              HttpStatus.BAD_REQUEST, 
              cause);
        this.entity = entity;
        this.rule = rule;
    }

    @Override
    public String getEntity() {
        return entity;
    }

    public String getRule() {
        return rule;
    }
}
