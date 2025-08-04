package com.marensovich.eljur.controller.Web;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.data.NotificationType;
import com.marensovich.eljur.exceptions.Exceptions.InvalidNotificationTypeException;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.*;
import com.marensovich.eljur.repository.*;
import com.marensovich.eljur.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepostory adminRepostory;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private JwtUtil jwtUtil;
    private Serializable ScoreService;
    @Autowired
    private ScoreService scoreService;

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
                throw new InvalidNotificationTypeException("Некорректный тип уведомлений");
            }
        }
        user.get().setNotificationMessages(notificationMessages);
        user.get().setNotificationHomework(notificationHomework);
        user.get().setNotificationScore(notificationScore);
        user.get().setNotificationNews(notificationNews);
        return ResponseEntity.ok(Map.of("message", "Settings successfully applied"));
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

        return ResponseEntity.status(HttpStatus.OK).body(Map.ofEntries(
                Map.entry("fullname", user.get().getFullname() != null ? user.get().getFullname() : "Не указано"),
                Map.entry("post", getPostDescription(user.get().getPost(), userID)),
                Map.entry("mail", user.get().getEmail() != null ? user.get().getEmail() : "Не указано"),
                Map.entry("phone", user.get().getPhone() != null ? user.get().getPhone() : "Не указано"),
                Map.entry("ProfileImage", user.get().getProfileImage() != null ? user.get().getProfileImage() : "Не указано"),
                Map.entry("TelegramID", user.get().getTelegramID() != null ? user.get().getTelegramID() : "Не указано"),
                Map.entry("group", getGroupDescription(userID)),
                Map.entry("avg_score", scoreService.getAvgScoreByUserID(userID)),
                Map.entry("notificationType", user.get().getNotificationType()),
                Map.entry("notificationMessages", user.get().isNotificationMessages()),
                Map.entry("notificationHomework", user.get().isNotificationHomework()),
                Map.entry("notificationScore", user.get().isNotificationScore()),
                Map.entry("notificationNews", user.get().isNotificationNews())
        ));

    }

    private String getGroupDescription(Integer userID) {
        Optional<Students> studentOptional = studentsRepository.findById(userID);
        if (studentOptional.isPresent()) {
            Integer groupId = studentOptional.get().getGroup();
            Optional<Groups> group = groupsRepository.findById(groupId);
            if (group.isPresent()) {
                return group.get().getGroup();
            } else {
                return "Группа не найдена для студента";
            }
        }
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherById(userID);
        if (teacherOptional.isPresent()) {
            Integer groupId = teacherRepository.getGroupId(userID);
            if (groupId != null) {
                Optional<Groups> group = groupsRepository.findById(groupId);
                return group.map(Object::toString).orElse("Группа не найдена для преподавателя");
            } else {
                return "Группа не назначена преподавателю";
            }
        }

        return "Пользователь не найден в базе данных студентов или преподавателей";
    }
    private String getPostDescription(String post, Integer userID) {
        return switch (post) {
            case "student" -> "Студент";
            case "teacher" -> "Преподаватель";
            case "admin" -> adminRepostory.findById(userID)
                    .map(admin -> adminRepostory.getAdminPost(userID))
                    .orElse("Неизвестный администратор");
            default -> "Неизвестная должность";
        };
    }
}

