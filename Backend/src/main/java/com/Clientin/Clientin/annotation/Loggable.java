package com.Clientin.Clientin.annotation;

import com.Clientin.Clientin.entity.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    
    /**
     * The log level for this operation
     */
    Log.LogLevel level() default Log.LogLevel.INFO;
    
    /**
     * The action type for this operation
     */
    Log.LogAction action() default Log.LogAction.PROCESS;
    
    /**
     * The entity being operated on
     */
    String entity() default "";
    
    /**
     * Custom message for the log entry
     */
    String message() default "";
    
    /**
     * Whether to log method parameters
     */
    boolean logParams() default false;
    
    /**
     * Whether to log return value
     */
    boolean logReturn() default false;
    
    /**
     * Whether to log execution time
     */
    boolean logExecutionTime() default true;
    
    /**
     * Whether to exclude this method from automatic logging
     */
    boolean exclude() default false;
}
