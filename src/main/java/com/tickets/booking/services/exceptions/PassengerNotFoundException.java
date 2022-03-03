package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PassengerNotFoundException extends RuntimeException{
    public PassengerNotFoundException() {
    }

    public PassengerNotFoundException(String message) {
        super(message);
    }

    public PassengerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PassengerNotFoundException(Throwable cause) {
        super(cause);
    }

    public PassengerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
