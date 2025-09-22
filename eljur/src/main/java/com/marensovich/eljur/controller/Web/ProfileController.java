package com.marensovich.eljur.controller.Web;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.*;
import com.marensovich.eljur.repository.*;
import com.marensovich.eljur.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProfileService profileService;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/setNotificationSettings")
    public ResponseEntity<?> setNotificationSettings(@RequestParam String token,
                                                     @RequestParam(required = false) String notificationType,
                                                     @RequestParam(required = false) Boolean notificationMessages,
                                                     @RequestParam(required = false) Boolean notificationHomework,
                                                     @RequestParam(required = false) Boolean notificationScore,
                                                     @RequestParam(required = false) Boolean notificationNews

    ) {
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userID);

        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");

        try {
            profileService.setNotificationSettings(
                    user,
                    notificationType,
                    notificationMessages,
                    notificationHomework,
                    notificationScore,
                    notificationNews
            );
            return ResponseEntity.ok(Map.of("message", "Settings successfully applied"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error while saving settings"));
        }
    }

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/setProfileImage")
    public ResponseEntity<?> setProfileImage(@RequestParam String token, String filename) {
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userID);

        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");

        user.get().setProfileImage(filename);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Settings successfully applied"));
    }
    

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getProfileInfo")
    public ResponseEntity<?> profileInfo(@RequestParam String token) {
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userID);

        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");

        Map profileInfo = profileService.getProfileInfo(user);

        return ResponseEntity.status(HttpStatus.OK).body(profileInfo);

    }
}

