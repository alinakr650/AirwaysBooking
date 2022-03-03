package com.tickets.booking.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MutuallyExclusiveSortingException extends RuntimeException{
    public MutuallyExclusiveSortingException() {
    }

    public MutuallyExclusiveSortingException(String message) {
        super(message);
    }

    public MutuallyExclusiveSortingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MutuallyExclusiveSortingException(Throwable cause) {
        super(cause);
    }

    public MutuallyExclusiveSortingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
