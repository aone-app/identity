package com.nerosoft.aone.identity.seedwork;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.CredentialException;
import java.io.IOException;

@RestControllerAdvice
class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException ex, HttpServletResponse response) {
        LOGGER.error("Illegal argument: ", ex);
        try {
            response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        } catch (IOException e) {
            // ignore
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CredentialException.class)
    public void handleCredentialException(CredentialException ex, HttpServletResponse response) {
        LOGGER.error("Credential error: ", ex);
        try {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        } catch (IOException e) {
            // ignore
        }
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurityException(SecurityException ex) {
        // Log the exception (you can use a logging framework like Logback or Log4j)
        LOGGER.error("Security error: ", ex);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletResponse response) {
        LOGGER.error("An error occurred: ", ex);

        int statusCode = switch (ex) {
            case IllegalArgumentException _ -> HttpStatus.BAD_REQUEST.value();
            case SecurityException _ -> HttpStatus.FORBIDDEN.value();
            case CredentialException _ -> HttpStatus.UNAUTHORIZED.value();
            case null, default -> HttpStatus.INTERNAL_SERVER_ERROR.value();
        };

        try {
            response.sendError(statusCode, ex.getMessage());
        } catch (IOException e) {
            // ignore
        }
    }
}
