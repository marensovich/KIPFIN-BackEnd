package com.marensovich.eljur.config.TelegramNotifier;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotNotifier telegramBotNotifier() {
        return new TelegramBotNotifier("7639553720:AAETD7HuaSI1MOH0Mza7QyNnP1_IMFLb2qk", "-1002357984483");
    }
}