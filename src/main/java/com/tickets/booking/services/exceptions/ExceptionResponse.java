package com.tickets.booking.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;


@Getter
@AllArgsConstructor
public class ExceptionResponse {

    Instant timestamp;
    String message;
    String details;

}
