package com.marensovich.eljur.service;

import com.marensovich.eljur.data.system.NotificationType;
import com.marensovich.eljur.data.system.PostTypes;
import com.marensovich.eljur.exceptions.Exceptions.InvalidNotificationTypeException;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.*;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * The type Profile service.
 */
@Service
public class ProfileService {

    @Autowired private UserRepository userRepository;
    @Autowired private ScoreService scoreService;
    @Autowired private StudentsRepository studentsRepository;
    @Autowired private GroupsRepository groupsRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AdminRepostory adminRepostory;


    /**
     * Set notification settings.
     *
     * @param userOptional         the user
     * @param notificationType     the notification type
     * @param notificationMessages the notification messages
     * @param notificationHomework the notification homework
     * @param notificationScore    the notification score
     * @param notificationNews     the notification news
     */
    public void setNotificationSettings(
            Optional<User> userOptional,
            @Nullable String notificationType,
            @Nullable Boolean notificationMessages,
            @Nullable Boolean notificationHomework,
            @Nullable Boolean notificationScore,
            @Nullable Boolean notificationNews
    ) {
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        Settings settings = user.getSettings();

        if (settings == null) {
            settings = new Settings();
            settings.setUser(user);
            user.setSettings(settings);
        }

        if (notificationType != null) {
            try {
                settings.setNotificationType(NotificationType.valueOf(notificationType));
            } catch (IllegalArgumentException e) {
                throw new InvalidNotificationTypeException("Unsupported notification type: " + notificationType);
            }
        }

        if (notificationMessages != null) {
            settings.setNotificationMessages(notificationMessages);
        }
        if (notificationHomework != null) {
            settings.setNotificationHomework(notificationHomework);
        }
        if (notificationScore != null) {
            settings.setNotificationScore(notificationScore);
        }
        if (notificationNews != null) {
            settings.setNotificationNews(notificationNews);
        }

        userRepository.save(user);
    }

    /**
     * Gets profile info.
     *
     * @param user the user
     * @return the profile info
     */
    public Map getProfileInfo(Optional<User> user) {
        return Map.ofEntries(
                Map.entry("fullname", user.get().getFullname() != null ? user.get().getFullname() : "Не указано"),
                Map.entry("post", getPostDescription(user.get().getPost(), user.get().getId())),
                Map.entry("mail", user.get().getEmail() != null ? user.get().getEmail() : "Не указано"),
                Map.entry("phone", user.get().getPhone() != null ? user.get().getPhone() : "Не указано"),
                Map.entry("ProfileImage", user.get().getSettings().getProfileImage() != null ? user.get().getSettings().getProfileImage() : "Не указано"),
                Map.entry("TelegramID", user.get().getTelegramId() != null ? user.get().getTelegramId() : "Не указано"),
                Map.entry("group", getGroupDescription(user.get().getId())),
                Map.entry("avg_score", scoreService.getAvgScoreByUserID(user.get().getId())),
                Map.entry("notificationType", user.get().getSettings().getNotificationType()),
                Map.entry("notificationMessages", user.get().getSettings().isNotificationMessages()),
                Map.entry("notificationHomework", user.get().getSettings().isNotificationHomework()),
                Map.entry("notificationScore", user.get().getSettings().isNotificationScore()),
                Map.entry("notificationNews", user.get().getSettings().isNotificationNews())
        );
    }



    private String getGroupDescription(Integer userID) {
        Optional<Students> studentOptional = studentsRepository.findById(userID.toString());
        if (studentOptional.isPresent()) {
            Integer groupId = studentOptional.get().getGroup().getId();
            Optional<Groups> group = groupsRepository.findById(groupId.toString());
            if (group.isPresent()) {
                return group.get().getName();
            } else {
                return "Группа не найдена для студента";
            }
        }
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherById(userID);
        if (teacherOptional.isPresent()) {
            Integer groupId = teacherRepository.getTeacherByUser_Id(userID);
            if (groupId != null) {
                Optional<Groups> group = groupsRepository.findById(groupId.toString());
                return group.map(Object::toString).orElse("Группа не найдена для преподавателя");
            } else {
                return "Группа не назначена преподавателю";
            }
        }

        return "Пользователь не найден в базе данных студентов или преподавателей";
    }
    private String getPostDescription(PostTypes post, Integer userID) {
        return switch (post) {
            case student -> "Студент";
            case teacher -> "Преподаватель";
            case admin -> adminRepostory.findById(userID)
                    .map(admin -> adminRepostory.getAdminsById(userID).getPost()).toString();
            default -> "Неизвестная должность";
        };
    }
}
