package com.example.shop.controller;

import com.example.shop.dtos.ErrorDto;
import com.example.shop.exceptions.*;
import com.example.shop.payment.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExpceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> hanleValidationErrors(MethodArgumentNotValidException exception ){
        var errors =new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach(error->{errors.put(error.getField(),error.getDefaultMessage());});
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler({PaymentException.class})
    public ResponseEntity<ErrorDto> PaymentException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Failed to create checkout session"));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorDto> UserNotFoundException(Exception e) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<ErrorDto> WrongPasswordException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(e.getMessage()));

    }
    @ExceptionHandler({NotFoundProductException.class})
    public ResponseEntity<ErrorDto> NotFoundProductException(Exception e) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler({NotAdminException.class})
    public ResponseEntity<ErrorDto> NotAdminException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<Void> handleOrderNotFoundException() {

        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDto> AccessDeniedException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler({NotFoundCartException.class})
    public ResponseEntity<ErrorDto> NotFoundCartException(Exception e) {

        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler({ CartEmptyException.class})
    public ResponseEntity<ErrorDto> CartEmptyException(Exception e) {

        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void>handlebadrequest() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
