package com.marensovich.eljur.controller.Web;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.Groups;

import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.GroupsRepository;
import com.marensovich.eljur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserContoller {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private JwtUtil jwtUtil;


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getUsernameByToken")
    public ResponseEntity<?> getUsernameByToken(@RequestParam String token) {
        try {
            if (token.chars().filter(ch -> ch == '.').count() != 2) {
                return ResponseEntity.status(400).body(Map.of("message", "Invalid JWT format"));
            }
            Integer userID = jwtUtil.getUserIdFromToken(token);
            Optional<User> userOptional = userRepository.findById(userID);
            if (userOptional.isPresent()) {
                String username = userOptional.get().getUsername();
                return ResponseEntity.ok().body(Map.of("username", username));
            } else {
                throw new UserNotFoundException("Пользователь не найден");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid or expired token"));
        }
    }

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getIDbyUsername")
    public ResponseEntity<?> getIDbyUsername(@RequestParam String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        return ResponseEntity.ok().body(Map.of("username", user.getUsername()));
    }

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getAllInfo")
    public ResponseEntity<?> getAllUserInfo(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");
        
        Optional<Groups> group = groupsRepository.getGroupById(user.get().getGroup());

        return ResponseEntity.ok(Map.ofEntries(
                Map.entry("fullname", user.get().getFullname() != null ? user.get().getFullname() : "Не указано"),
                Map.entry("post", user.get().getPost()),
                Map.entry("mail", user.get().getEmail() != null ? user.get().getEmail() : "Не указано"),
                Map.entry("phone", user.get().getPhone() != null ? user.get().getPhone() : "Не указано"),
                Map.entry("ProfileImage", user.get().getProfileImage() != null ? user.get().getProfileImage() : "Не указано"),
                Map.entry("TelegramID", user.get().getTelegramID() != null ? user.get().getTelegramID() : "Не указано"),
                Map.entry("group", group.get().getGroup()),
                Map.entry("avg_score", "avg_score"),
                Map.entry("notificationType", user.get().getNotificationType()),
                Map.entry("notificationMessages", user.get().isNotificationMessages()),
                Map.entry("notificationHomework", user.get().isNotificationHomework()),
                Map.entry("notificationScore", user.get().isNotificationScore()),
                Map.entry("notificationNews", user.get().isNotificationNews()),
                Map.entry("blackTheme", user.get().isBlack_theme())
        ));
    }




}



