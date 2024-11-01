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
    @ApiResponse(responseCode = "500", description = "Global Exception handler", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    @ApiResponse(responseCode = "401", description = "Unauthorized - The bearer token is not valid or is expired.", content = @Content())
    public ResponseEntity<ExceptionDto> handleJwtException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto("invalid token", e.getClass().toString()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(jakarta.validation.ValidationException.class)
    public ResponseEntity<ExceptionDto> handleConstraintViolation(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDto> handleDataIntegrityViolationException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateUserException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto("Bad credentials", e.getClass().getName()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDeniedException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDto> handleHttpMessageNotReadableException(Exception e, WebRequest request) {
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(e),
                HttpStatus.BAD_REQUEST);
    }

}
