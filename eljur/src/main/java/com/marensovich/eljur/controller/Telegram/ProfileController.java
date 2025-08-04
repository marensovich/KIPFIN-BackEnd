package com.marensovich.eljur.controller.Telegram;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.Groups;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.Teacher;
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
@RequestMapping("/api/telegram/profile")
@Controller("telegramProfileController")
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


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getProfileInfo")
    public ResponseEntity<?> profileInfo(@RequestParam Integer userID) {
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
                Map.entry("avg_score", "avg_score"),
                Map.entry("notificationType", user.get().getNotificationType()),
                Map.entry("notificationMessages", user.get().isNotificationMessages()),
                Map.entry("notificationHomework", user.get().isNotificationHomework()),
                Map.entry("notificationScore", user.get().isNotificationScore()),
                Map.entry("notificationNews", user.get().isNotificationNews()),
                Map.entry("blackTheme", user.get().isBlack_theme())
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

