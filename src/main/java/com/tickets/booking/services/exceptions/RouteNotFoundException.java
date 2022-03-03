package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException() {
    }

    public RouteNotFoundException(String message) {
        super(message);
    }

    public RouteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteNotFoundException(Throwable cause) {
        super(cause);
    }

    public RouteNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
