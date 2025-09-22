package com.marensovich.eljur.exceptions;

import com.marensovich.eljur.config.TelegramNotifier.TelegramBotNotifier;
import com.marensovich.eljur.exceptions.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final TelegramBotNotifier telegramBotNotifier;

    public GlobalExceptionHandler(TelegramBotNotifier telegramBotNotifier) {
        this.telegramBotNotifier = telegramBotNotifier;
    }

    private ResponseEntity<ApiError> buildErrorResponse(
            Exception e,
            HttpStatus status,
            HttpServletRequest request
    ) {
        telegramBotNotifier.sendMessage("Ошибка на сервере: " + e.getMessage());
        return ResponseEntity.status(status).body(new ApiError(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPassword(InvalidPasswordException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.UNAUTHORIZED, req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, req);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiError> handleFileNotFound(FileNotFoundException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(InvalidNotificationTypeException.class)
    public ResponseEntity<ApiError> handleInvalidNotificationType(InvalidNotificationTypeException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(InvalidRegistrationCodeException.class)
    public ResponseEntity<ApiError> handleInvalidRegistrationCode(InvalidRegistrationCodeException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(ActivatedRegistrationCodeException.class)
    public ResponseEntity<ApiError> handleActivatedRegistrationCode(ActivatedRegistrationCodeException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, req);
    }
}