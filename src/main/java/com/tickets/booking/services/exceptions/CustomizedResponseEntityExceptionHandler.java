package com.tickets.booking.services.exceptions;

import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.time.Instant;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(FlightNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(RouteNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(PassengerNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(TicketNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatNotAvailableException.class)
    public final ResponseEntity<Object> handleAllExceptions(SeatNotAvailableException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.SEE_OTHER);
    }

    @ExceptionHandler(MutuallyExclusiveSortingException.class)
    public final ResponseEntity<Object> handleAllExceptions(MutuallyExclusiveSortingException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(UserNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<Object> handleAllExceptions(UserAlreadyRegisteredException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<Object> handleAllExceptions(AuthenticationException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public final ResponseEntity<Object> handleAllExceptions(IllegalAccessException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public final ResponseEntity<Object> handleAllExceptions(NonUniqueResultException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(EntityNotFoundException ex, WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatus httpStatus,
                                                                  WebRequest webRequest)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now(),
                "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
