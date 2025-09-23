package com.marensovich.eljur.config.TelegramNotifier;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotNotifier telegramBotNotifier() {
        return new TelegramBotNotifier("7639553720:AAFFd33rhCZEYjKngnZaB1f2MNfnJ8L82Gc", "6737078498");
    }
}