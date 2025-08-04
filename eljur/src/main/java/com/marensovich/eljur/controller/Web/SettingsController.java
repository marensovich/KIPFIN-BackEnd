package com.marensovich.eljur.controller.Web;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.data.NotificationType;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/setNotificationSettings")
    public ResponseEntity<?> setNotificationSettings(@RequestParam String token,
                                                     @RequestParam String notificationType,
                                                     @RequestParam Boolean notificationMessages,
                                                     @RequestParam Boolean notificationHomework,
                                                     @RequestParam Boolean notificationScore,
                                                     @RequestParam Boolean notificationNews
    ) {
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");
        switch (notificationType) {
            case "Without_Notification" -> user.get().setNotificationType(NotificationType.Without_Notification);
            case "Email" -> user.get().setNotificationType(NotificationType.Email);
            case "Telegram" -> user.get().setNotificationType(NotificationType.Telegram);
            default -> {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid Notification Type"));
            }
        }
        user.get().setNotificationMessages(notificationMessages);
        user.get().setNotificationHomework(notificationHomework);
        user.get().setNotificationScore(notificationScore);
        user.get().setNotificationNews(notificationNews);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Settings successfully applied"));
    }

}
