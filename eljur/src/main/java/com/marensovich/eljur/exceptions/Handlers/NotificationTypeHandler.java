package com.marensovich.eljur.exceptions.Handlers;

import com.marensovich.eljur.exceptions.Exceptions.InvalidNotificationTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class NotificationTypeHandler {

    @ExceptionHandler(InvalidNotificationTypeException.class)
    public ResponseEntity<?> handleInvalidNotificationTypeException(InvalidNotificationTypeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

}
