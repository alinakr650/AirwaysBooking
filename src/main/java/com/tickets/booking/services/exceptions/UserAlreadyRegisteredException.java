package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException() {
    }

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

    public UserAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
