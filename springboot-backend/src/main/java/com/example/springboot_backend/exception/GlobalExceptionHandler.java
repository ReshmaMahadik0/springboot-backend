package com.example.springboot_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>  handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(404, ex.getMessage(), "User not found", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse>  handleUserNotFoundException(DuplicateUserException ex){
        ErrorResponse errorResponse = new ErrorResponse(409, ex.getMessage(), "User already exist with email", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

    }

}
