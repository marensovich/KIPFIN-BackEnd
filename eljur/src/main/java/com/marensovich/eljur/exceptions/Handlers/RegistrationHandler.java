package com.marensovich.eljur.exceptions.Handlers;

import com.marensovich.eljur.exceptions.Exceptions.ActivatedRegistrationCodeException;
import com.marensovich.eljur.exceptions.Exceptions.InvalidRegistrationCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RegistrationHandler {

    @ExceptionHandler(InvalidRegistrationCodeException.class)
    public ResponseEntity<?> handleInvalidRegistrationCodeException(InvalidRegistrationCodeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(ActivatedRegistrationCodeException.class)
    public ResponseEntity<?> handleActivatedRegistrationCodeException(ActivatedRegistrationCodeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

}
