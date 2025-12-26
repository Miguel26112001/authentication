package com.example.authentication.shared.interfaces.rest.exceptions;

import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResource> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new MessageResource(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResource> handleInternalServerError(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MessageResource("An unexpected error occurred."));
    }
}
