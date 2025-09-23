package com.marensovich.eljur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration class for Redis.
 * <p>
 * Provides a {@link RedisTemplate} bean for interacting with Redis.
 * </p>
 *
 * @author marensovich
 * @version 0.1
 * @since 0.1
 */
@Configuration
public class RedisConfig {

    /**
     * Configures a RedisTemplate with String keys and values.
     *
     * @param connectionFactory the Redis connection factory
     * @return a {@link RedisTemplate} instance
     * @since 0.1
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
