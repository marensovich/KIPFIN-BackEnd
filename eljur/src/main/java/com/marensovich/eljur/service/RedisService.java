package com.marensovich.eljur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisService {
    @Autowired
    private RedisConnectionFactory connectionFactory;

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // работа со строками
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value); // обычное key-value
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // работа со списками
    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public String popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    // работа с множествами
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    public Map<String, String> getAllKeyValues() {
        Set<String> keys = redisTemplate.keys("*"); // все ключи
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }

        List<String> values = redisTemplate.opsForValue().multiGet(keys);

        Map<String, String> result = new LinkedHashMap<>();
        int i = 0;
        for (String key : keys) {
            result.put(key, values.get(i++));
        }
        return result;
    }



    // pub/sub (отправить сообщение в канал)
    public void publish(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public boolean checkConnection() {
        try (var connection = connectionFactory.getConnection()) {
            return "PONG".equals(connection.ping());
        }
    }
}
