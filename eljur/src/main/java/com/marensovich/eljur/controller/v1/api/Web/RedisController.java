package com.marensovich.eljur.controller.v1.api.Web;

import com.marensovich.eljur.service.RedisService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for Redis operations.
 * @version 0.1
 * @since 0.1
 * @author marensovich
 */
@RestController
@RequestMapping("api/v1/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Saved!";
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisService.getValue(key);
    }

    @GetMapping("/all")
    public Map<String, Object> getAll() {
        return redisService.getAllKeyValues();
    }
}
