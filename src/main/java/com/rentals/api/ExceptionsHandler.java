package com.rentals.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.rentals.api.Exceptions.DuplicateUserException;
import com.rentals.api.Exceptions.ResourceNotFoundException;
import com.rentals.api.dto.ExceptionDto;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Global Exception handler", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<Object> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponse(responseCode = "401", description = "Unauthorized - The bearer token is not valid or is expired.", content = @Content())
    public ResponseEntity<Object> handleJwtException(Exception e, WebRequest request) {
        return new ResponseEntity<Object>(
                new ExceptionDto("invalid token", e.getClass().toString()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(jakarta.validation.ValidationException.class)
    public ResponseEntity<Object> handleConstraintViolation(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception e, WebRequest request) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Object> handleDuplicateUserException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleAuthenticationException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto("Bad credentials", e.getClass().getName()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

}
