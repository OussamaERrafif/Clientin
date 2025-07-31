package com.Clientin.Clientin.exception;

import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for all REST controllers
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            ForbiddenException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(
            BusinessRuleException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(
            ServiceException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalServiceException(
            ExternalServiceException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(
            DatabaseException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(FileOperationException.class)
    public ResponseEntity<ErrorResponse> handleFileOperationException(
            FileOperationException ex, WebRequest request) {
        
        logException(ex, request);
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getHttpStatus().value(),
            ex.getHttpStatus().getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            ex.getErrorCode(),
            ex.getEntity()
        );
        errorResponse.setDetails(ex.getDetails());
        
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        log.error("Validation error: {}", ex.getMessage());
        logService.logError("Validation", null, "system", 
                          "Request validation failed: " + ex.getMessage(),
                          ex.getStackTrace().toString());
        
        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ValidationError(
                    error.getField(),
                    error.getRejectedValue(),
                    error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Validation failed",
            getPath(request),
            "VALIDATION_ERROR",
            "Request"
        );
        errorResponse.setValidationErrors(validationErrors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        
        log.error("Constraint violation: {}", ex.getMessage());
        logService.logError("Validation", null, "system", 
                          "Constraint validation failed: " + ex.getMessage(),
                          ex.getStackTrace().toString());
        
        List<ErrorResponse.ValidationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse.ValidationError(
                    violation.getPropertyPath().toString(),
                    violation.getInvalidValue(),
                    violation.getMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Constraint validation failed",
            getPath(request),
            "CONSTRAINT_VIOLATION",
            "Request"
        );
        errorResponse.setValidationErrors(validationErrors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        log.error("Type mismatch error: {}", ex.getMessage());
        logService.logError("Request", null, "system", 
                          "Method argument type mismatch: " + ex.getMessage(),
                          ex.getStackTrace().toString());
        
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            message,
            getPath(request),
            "TYPE_MISMATCH",
            "Request"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        
        log.error("Message not readable: {}", ex.getMessage());
        logService.logError("Request", null, "system", 
                          "HTTP message not readable: " + ex.getMessage(),
                          ex.getStackTrace().toString());
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Malformed JSON request",
            getPath(request),
            "MALFORMED_REQUEST",
            "Request"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        
        log.error("Entity not found: {}", ex.getMessage());
        logService.logError("Entity", null, "system", 
                          "Entity not found: " + ex.getMessage(),
                          ex.getStackTrace().toString());
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            getPath(request),
            "ENTITY_NOT_FOUND",
            "Entity"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        logService.logError("System", null, "system", 
                          "Unexpected error: " + ex.getMessage(),
                          ex.getStackTrace().toString());
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "An unexpected error occurred",
            getPath(request),
            "INTERNAL_ERROR",
            "System"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logException(BaseException ex, WebRequest request) {
        log.error("Custom exception [{}]: {}", ex.getErrorCode(), ex.getMessage());
        logService.logError(ex.getEntity(), null, "system", 
                          String.format("Exception [%s]: %s", ex.getErrorCode(), ex.getMessage()),
                          ex.getDetails() != null ? ex.getDetails() : ex.getStackTrace().toString());
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
