package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FlightNotFoundException extends RuntimeException{
    public FlightNotFoundException() {
    }

    public FlightNotFoundException(String message) {
        super(message);
    }

    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightNotFoundException(Throwable cause) {
        super(cause);
    }

    public FlightNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
