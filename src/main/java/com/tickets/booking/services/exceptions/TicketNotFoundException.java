package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {
    }

    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketNotFoundException(Throwable cause) {
        super(cause);
    }

    public TicketNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
