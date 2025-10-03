package com.nerosoft.aone.identity.seedwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.login.CredentialException;

@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // Log the exception (you can use a logging framework like Logback or Log4j)
        LOGGER.error("An error occurred: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<?> handleCredentialException(CredentialException ex) {
        // Log the exception (you can use a logging framework like Logback or Log4j)
        LOGGER.error("Credential error: ", ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurityException(SecurityException ex) {
        // Log the exception (you can use a logging framework like Logback or Log4j)
        LOGGER.error("Security error: ", ex);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Log the exception (you can use a logging framework like Logback or Log4j)
        LOGGER.error("Illegal argument: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
