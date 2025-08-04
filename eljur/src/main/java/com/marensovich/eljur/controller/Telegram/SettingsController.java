package com.marensovich.eljur.controller.Telegram;

import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@Controller("telegramSettingsController")
@RequestMapping("/api/telegram/settings")
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @PostMapping("/setNotificationSettings")
    public ResponseEntity<?> setNotificationSettings(@RequestBody Map<String, Object> payload) {
        Integer userID = (Integer) payload.get("userID");
        Boolean notificationMessages = (Boolean) payload.get("notificationMessages");
        Boolean notificationHomework = (Boolean) payload.get("notificationHomework");
        Boolean notificationScore = (Boolean) payload.get("notificationScore");
        Boolean notificationNews = (Boolean) payload.get("notificationNews");

        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");

        user.get().setNotificationMessages(notificationMessages);
        user.get().setNotificationHomework(notificationHomework);
        user.get().setNotificationScore(notificationScore);
        user.get().setNotificationNews(notificationNews);
        userRepository.save(user.get());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Settings successfully applied"));
    }

}
