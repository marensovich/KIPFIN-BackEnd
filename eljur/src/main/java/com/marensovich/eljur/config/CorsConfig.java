package com.marensovich.eljur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Применяем CORS ко всем эндпоинтам
                        .allowedOrigins("http://199.83.103.127:25323") // Разрешенный источник
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные HTTP-методы
                        .allowedHeaders("*") // Разрешенные заголовки
                        .allowCredentials(true) // Разрешаем передачу cookies/credentials
                        .maxAge(3600); // Кэширование предварительных запросов (в секундах)
            }
        };
    }
}