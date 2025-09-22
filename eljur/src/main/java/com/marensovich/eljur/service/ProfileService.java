package com.marensovich.eljur.service;

import com.marensovich.eljur.data.NotificationType;
import com.marensovich.eljur.exceptions.Exceptions.InvalidNotificationTypeException;
import com.marensovich.eljur.model.Groups;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.Teacher;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired private UserRepository userRepository;
    @Autowired private ScoreService scoreService;
    @Autowired private StudentsRepository studentsRepository;
    @Autowired private GroupsRepository groupsRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AdminRepostory adminRepostory;


    public void setNotificationSettings(
            Optional<User> user,
            @Nullable String notificationType,
            @Nullable Boolean notificationMessages,
            @Nullable Boolean notificationHomework,
            @Nullable Boolean notificationScore,
            @Nullable Boolean notificationNews
    ){

        switch (NotificationType.valueOf(notificationType)) {
            case Without_Notification -> user.get().setNotificationType(NotificationType.Without_Notification);
            case Email -> user.get().setNotificationType(NotificationType.Email);
            case Telegram -> user.get().setNotificationType(NotificationType.Telegram);
            default -> {
                throw new InvalidNotificationTypeException("Некорректный тип уведомлений");
            }
        }
        if (notificationMessages != null) {
            user.get().setNotificationMessages(notificationMessages);
        }
        if (notificationHomework != null) {
            user.get().setNotificationHomework(notificationHomework);
        }
        if (notificationScore != null) {
            user.get().setNotificationScore(notificationScore);
        }
        if (notificationNews != null) {
            user.get().setNotificationNews(notificationNews);
        }
        userRepository.save(user.get());
    }

    public Map getProfileInfo(Optional<User> user) {
        return Map.ofEntries(
                Map.entry("fullname", user.get().getFullname() != null ? user.get().getFullname() : "Не указано"),
                Map.entry("post", getPostDescription(user.get().getPost(), user.get().getId())),
                Map.entry("mail", user.get().getEmail() != null ? user.get().getEmail() : "Не указано"),
                Map.entry("phone", user.get().getPhone() != null ? user.get().getPhone() : "Не указано"),
                Map.entry("ProfileImage", user.get().getProfileImage() != null ? user.get().getProfileImage() : "Не указано"),
                Map.entry("TelegramID", user.get().getTelegramID() != null ? user.get().getTelegramID() : "Не указано"),
                Map.entry("group", getGroupDescription(user.get().getId())),
                Map.entry("avg_score", scoreService.getAvgScoreByUserID(user.get().getId())),
                Map.entry("notificationType", user.get().getNotificationType()),
                Map.entry("notificationMessages", user.get().isNotificationMessages()),
                Map.entry("notificationHomework", user.get().isNotificationHomework()),
                Map.entry("notificationScore", user.get().isNotificationScore()),
                Map.entry("notificationNews", user.get().isNotificationNews()),
                Map.entry("blackTheme", user.get().isBlack_theme())
        );
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
