package com.marensovich.eljur.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marensovich.eljur.config.TelegramNotifier.TelegramBotNotifier;
import com.marensovich.eljur.exceptions.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired private TelegramBotNotifier telegramBotNotifier;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private ResponseEntity<ApiError> buildErrorResponse(
            Exception e,
            HttpStatus status,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiError);

            String escapedJson = TelegramBotNotifier.escapeMarkdownV2(json);
            String msg = "Ошибка на сервере:\n```json\n" + escapedJson + "\n```";

            telegramBotNotifier.sendMessage(msg);

        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.status(status).body(apiError);
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

    @ExceptionHandler(RegCodeNotFound.class)
    public ResponseEntity<ApiError> handleRegCodeNotFound(RegCodeNotFound e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(InvalidJwtTokenFormat.class)
    public ResponseEntity<ApiError> handleInvalidJwtTokenFormat(InvalidJwtTokenFormat e, HttpServletRequest req) {
        return buildErrorResponse(e, HttpStatus.UNAUTHORIZED, req);
    }
}