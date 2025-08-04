package com.marensovich.eljur.config.TelegramNotifier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final TelegramBotNotifier telegramBotNotifier;

    public GlobalExceptionHandler(TelegramBotNotifier telegramBotNotifier) {
        this.telegramBotNotifier = telegramBotNotifier;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        telegramBotNotifier.sendMessage("Ошибка на сервере: " + e.getMessage());

        return "Произошла ошибка на сервере: " + e.getMessage();
    }
}
