package com.marensovich.eljur.service;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for interacting with Redis.
 * <p>
 * Provides basic operations for Strings, Lists, Sets, Pub/Sub and full key-value access.
 * </p>
 * @since 0.1
 * @version 0.1
 * @author marensovich
 */
@Service
public class RedisService {

    private final RedisConnectionFactory connectionFactory;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory connectionFactory) {
        this.redisTemplate = redisTemplate;
        this.connectionFactory = connectionFactory;
    }

    /**
     * Sets a string value for a given key.
     *
     * @param key   the key
     * @param value the value
     * @since 0.1
     */
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Retrieves the value for a given key.
     *
     * @param key the key
     * @return the value or null if not exists
     * @since 0.1
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Adds a value to the end of a list.
     *
     * @param key   the list key
     * @param value the value to add
     * @since 0.1
     */
    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * Pops the first value from a list.
     *
     * @param key the list key
     * @return the first element or null if empty
     * @since 0.1
     */
    public Object popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * Adds a value to a set.
     *
     * @param key   the set key
     * @param value the value to add
     * @since 0.1
     */
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * Retrieves all members of a set.
     *
     * @param key the set key
     * @return set of values
     * @since 0.1
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Retrieves all key-value pairs from Redis.
     *
     * @return map of key-value pairs
     * @since 0.1
     */
    public Map<String, Object> getAllKeyValues() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Object> values = redisTemplate.opsForValue().multiGet(keys);
        Map<String, Object> result = new LinkedHashMap<>();
        int i = 0;
        for (String key : keys) {
            result.put(key, values.get(i++));
        }
        return result;
    }

    /**
     * Publishes a message to a channel.
     *
     * @param channel the channel name
     * @param message the message to publish
     * @since 0.1
     */
    public void publish(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    /**
     * Checks if Redis is reachable.
     *
     * @return true if connection succeeds, false otherwise
     * @since 0.1
     */
    public boolean checkConnection() {
        try (var connection = connectionFactory.getConnection()) {
            return "PONG".equals(connection.ping());
        }
    }
}
