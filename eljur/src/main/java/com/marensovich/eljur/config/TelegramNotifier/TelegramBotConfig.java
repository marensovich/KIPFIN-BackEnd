package com.marensovich.eljur.config.TelegramNotifier;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Telegram bot config.
 */
@Configuration
public class TelegramBotConfig {

    /**
     * The Bot token.
     */
    String botToken = System.getenv("TELEGRAM_LOGGER_TOKEN");
    /**
     * The Chat id.
     */
    String chatId = System.getenv("TELEGRAM_LOGGER_CHAT_ID");

    /**
     * Telegram bot notifier telegram bot notifier.
     *
     * @return the telegram bot notifier
     */
    @Bean
    public TelegramBotNotifier telegramBotNotifier() {
        return new TelegramBotNotifier(botToken, chatId);
    }
}