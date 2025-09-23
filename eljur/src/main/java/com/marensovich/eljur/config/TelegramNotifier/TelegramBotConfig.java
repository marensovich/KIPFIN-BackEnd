package com.marensovich.eljur.config.TelegramNotifier;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    String botToken = System.getenv("TELEGRAM_LOGGER_TOKEN");
    String chatId = System.getenv("TELEGRAM_LOGGER_CHAT_ID");

    @Bean
    public TelegramBotNotifier telegramBotNotifier() {
        return new TelegramBotNotifier(botToken, chatId);
    }
}