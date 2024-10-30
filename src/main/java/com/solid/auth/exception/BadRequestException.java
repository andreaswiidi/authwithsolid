package com.solid.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * S -> responsibility is to encapsulate the HTTP status and error message.
 * O -> it's not open for extension
 * L,I -> because this class is the one that implementing
 */

@Getter
@Setter
public class BadRequestException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorMessage;

    public BadRequestException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorMessage = message;
    }
}
