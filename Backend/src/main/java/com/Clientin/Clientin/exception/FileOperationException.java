package com.Clientin.Clientin.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when file operations fail
 */
public class FileOperationException extends BaseException {
    
    private static final String ERROR_CODE = "FILE_OPERATION_ERROR";
    private final String fileName;
    private final String operation;

    public FileOperationException(String fileName, String operation, String message) {
        super(String.format("File operation '%s' failed for file '%s': %s", operation, fileName, message), 
              ERROR_CODE, 
              HttpStatus.INTERNAL_SERVER_ERROR);
        this.fileName = fileName;
        this.operation = operation;
    }

    public FileOperationException(String fileName, String operation, String message, Throwable cause) {
        super(String.format("File operation '%s' failed for file '%s': %s", operation, fileName, message), 
              ERROR_CODE, 
              HttpStatus.INTERNAL_SERVER_ERROR, 
              cause);
        this.fileName = fileName;
        this.operation = operation;
    }

    public FileOperationException(String fileName, String operation, String message, String details, Throwable cause) {
        super(String.format("File operation '%s' failed for file '%s': %s", operation, fileName, message), 
              ERROR_CODE, 
              HttpStatus.INTERNAL_SERVER_ERROR, 
              details, 
              cause);
        this.fileName = fileName;
        this.operation = operation;
    }

    @Override
    public String getEntity() {
        return "File";
    }

    public String getFileName() {
        return fileName;
    }

    public String getOperation() {
        return operation;
    }
}
