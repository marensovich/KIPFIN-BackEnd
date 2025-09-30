package com.marensovich.eljur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis configuration class.
 * <p>
 * Provides a {@link RedisTemplate} bean for interacting with Redis using
 * String keys and Object values serialized as JSON.
 * </p>
 * @author marensovich
 * @version 0.1
 * @since 0.1
 */
@Configuration
public class RedisConfig {

    /**
     * Creates a RedisTemplate with String keys and Object values.
     *
     * @param connectionFactory the Redis connection factory
     * @return configured RedisTemplate instance
     * @since 0.1
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}
