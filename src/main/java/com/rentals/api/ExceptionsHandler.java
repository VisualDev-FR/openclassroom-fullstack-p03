package com.rentals.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.rentals.api.Exceptions.ResourceNotFoundException;
import com.rentals.api.dto.ExceptionDto;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleJwtException(Exception e, WebRequest request) {
        return new ResponseEntity<Object>(
                new ExceptionDto("invalid token", e.getClass().toString()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(jakarta.validation.ValidationException.class)
    public ResponseEntity<Object> handleConstraintViolation(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.NOT_FOUND);
    }
}
