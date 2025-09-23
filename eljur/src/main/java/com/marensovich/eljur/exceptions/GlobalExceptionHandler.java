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

/**
 * Global exception handler for the application.
 *
 * <p>
 * Converts thrown exceptions into {@link ApiError} responses
 * and sends detailed error information to Telegram
 * using {@link TelegramBotNotifier}.
 * </p>
 *
 * <p>
 * Each exception type is mapped to a specific HTTP status code.
 * </p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private TelegramBotNotifier telegramBotNotifier;

    private final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Builds a standardized {@link ApiError} response and sends
     * the error details to Telegram.
     *
     * @param e       the exception
     * @param status  corresponding HTTP status
     * @param request the current HTTP request
     * @return {@link ResponseEntity} containing the error payload
     * @since v.0.1
     */
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
            String json = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(apiError);

            String escapedJson = TelegramBotNotifier.escapeMarkdownV2(json);
            String msg = "Ошибка на сервере:\n```json\n" + escapedJson + "\n```";

            telegramBotNotifier.sendMessage(msg);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.status(status).body(apiError);
    }

    // ======================
    // === Error Handlers ===
    // ======================

    /**
     * Handles {@link ActivatedRegistrationCodeException}.
     *
     * @return 400 Bad Request
     * @since v.0.1
     */
    @ExceptionHandler(ActivatedRegistrationCodeException.class)
    public ResponseEntity<ApiError> handleActivatedRegistrationCode(
            ActivatedRegistrationCodeException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, req);
    }

    /**
     * Handles {@link InvalidNotificationTypeException}.
     *
     * @return 400 Bad Request
     * @since v.0.1
     */
    @ExceptionHandler(InvalidNotificationTypeException.class)
    public ResponseEntity<ApiError> handleInvalidNotificationType(
            InvalidNotificationTypeException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, req);
    }

    /**
     * Handles {@link InvalidTokenException}.
     *
     * @return 400 Bad Request
     * @since v.0.1
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(
            InvalidTokenException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, req);
    }

    /**
     * Handles {@link InvalidPasswordException}.
     *
     * @return 401 Unauthorized
     * @since v.0.1
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPassword(
            InvalidPasswordException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.UNAUTHORIZED, req);
    }

    /**
     * Handles {@link InvalidJwtTokenFormat}.
     *
     * @return 401 Unauthorized
     * @since v.0.1
     */
    @ExceptionHandler(InvalidJwtTokenFormat.class)
    public ResponseEntity<ApiError> handleInvalidJwtTokenFormat(
            InvalidJwtTokenFormat e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.UNAUTHORIZED, req);
    }

    /**
     * Handles {@link UserNotFoundException}.
     *
     * @return 404 Not Found
     * @since v.0.1
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    /**
     * Handles {@link FileNotFoundException}.
     *
     * @return 404 Not Found
     * @since v.0.1
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiError> handleFileNotFound(
            FileNotFoundException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    /**
     * Handles {@link InvalidRegistrationCodeException}.
     *
     * @return 404 Not Found
     * @since v.0.1
     */
    @ExceptionHandler(InvalidRegistrationCodeException.class)
    public ResponseEntity<ApiError> handleInvalidRegistrationCode(
            InvalidRegistrationCodeException e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    /**
     * Handles {@link RegCodeNotFound}.
     *
     * @return 404 Not Found
     * @since v.0.1
     */
    @ExceptionHandler(RegCodeNotFound.class)
    public ResponseEntity<ApiError> handleRegCodeNotFound(
            RegCodeNotFound e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, req);
    }

    /**
     * Handles all unhandled exceptions.
     *
     * @return 500 Internal Server Error
     * @since v.0.1
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(
            Exception e,
            HttpServletRequest req
    ) {
        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, req);
    }
}
