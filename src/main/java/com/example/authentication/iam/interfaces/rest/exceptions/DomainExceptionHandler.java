package com.example.authentication.iam.interfaces.rest.exceptions;

import com.example.authentication.iam.domain.exceptions.*;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DomainExceptionHandler {
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<MessageResource> handleRoleNotFound(RoleNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new MessageResource(e.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<MessageResource> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new MessageResource(e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResource> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new MessageResource(e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<MessageResource> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new MessageResource(e.getMessage()));
    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<MessageResource> handleUserNotActiveException(UserNotActiveException e) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new MessageResource(e.getMessage()));
    }
}
