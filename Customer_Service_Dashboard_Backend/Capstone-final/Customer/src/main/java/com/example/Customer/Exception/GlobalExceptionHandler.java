package com.example.Customer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
 
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
 
    @ExceptionHandler(TicketCreationException.class)
    public ResponseEntity<?> handleTicketCreationException(TicketCreationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @ExceptionHandler(NoRepresentativeFoundException.class)
    public ResponseEntity<?> handleNoRepresentativeFoundException(NoRepresentativeFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
 

	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleAllExceptions(Exception e) {
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

