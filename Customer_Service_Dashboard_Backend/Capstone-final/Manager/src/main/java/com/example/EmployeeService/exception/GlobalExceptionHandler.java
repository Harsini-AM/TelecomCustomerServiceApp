package com.example.EmployeeService.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String getStatusLabel(HttpStatus status) {
        return status.getReasonPhrase();
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<String> handleDuplicateEntryException(DuplicateEntryException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        logger.error("DuplicateEntryException: {}, Status Code: {} - {}", e.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>(e.getMessage(), status);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<String> handleManagerNotFoundException(ManagerNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        logger.error("ManagerNotFoundException: {}, Status Code: {} - {}", e.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>(e.getMessage(), status);
    }

    @ExceptionHandler(ManagerHasRepresentativesException.class)
    public ResponseEntity<String> handleManagerHasRepresentativesException(ManagerHasRepresentativesException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logger.error("ManagerHasRepresentativesException: {}, Status Code: {} - {}", ex.getMessage(), status.value(), getStatusLabel(status));
        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("Exception: {}, Status Code: {} - {}", e.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), status);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        logger.error("EmployeeNotFoundException: {}, Status Code: {} - {}", ex.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        logger.error("UnauthorizedAccessException: {}, Status Code: {} - {}", ex.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<String> handleInvalidRoleException(InvalidRoleException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logger.error("InvalidRoleException: {}, Status Code: {} - {}", ex.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> handleInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("InternalServerErrorException: {}, Status Code: {} - {}", ex.getMessage(), status.value(), getStatusLabel(status));
        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
