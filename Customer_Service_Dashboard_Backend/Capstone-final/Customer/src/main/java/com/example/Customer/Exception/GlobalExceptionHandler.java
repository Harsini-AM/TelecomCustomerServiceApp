package com.example.Customer.Exception;

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

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        logger.error("Status Code: {}, Error: {}", status.value(), ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), status);
    }
 
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logger.error("Status Code: {}, Error: {}", status.value(), ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), status);
    }
 
    @ExceptionHandler(TicketCreationException.class)
    public ResponseEntity<?> handleTicketCreationException(TicketCreationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("Status Code: {}, Error: {}", status.value(), ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), status);
    }
 
    @ExceptionHandler(NoRepresentativeFoundException.class)
    public ResponseEntity<?> handleNoRepresentativeFoundException(NoRepresentativeFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        logger.error("Status Code: {}, Error: {}", status.value(), ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), status);
    }
 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("Status Code: {}, Error: {}", status.value(), e.getMessage());
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), status);
    }
}
