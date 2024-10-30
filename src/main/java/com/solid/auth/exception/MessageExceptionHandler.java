package com.solid.auth.exception;

import com.solid.auth.dto.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * S -> responsibility class handles exceptions related to the application.
 * O -> you can add new @ExceptionHandler methods for different exceptions
 */

@RestControllerAdvice
public class MessageExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ResponseWrapper<>().fail(ex.getErrorMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String messageError = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            messageError = error.getDefaultMessage();
        }
        return ResponseEntity.status(ex.getStatusCode()).body(new ResponseWrapper<>().fail(messageError));
    }
}
