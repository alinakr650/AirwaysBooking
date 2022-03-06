package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InvalidEmailOrPasswordException extends RuntimeException{
    public InvalidEmailOrPasswordException() {
    }

    public InvalidEmailOrPasswordException(String message) {
        super(message);
    }

    public InvalidEmailOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailOrPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidEmailOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
