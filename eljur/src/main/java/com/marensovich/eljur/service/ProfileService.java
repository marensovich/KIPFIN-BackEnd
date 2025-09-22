package com.marensovich.eljur.service;

import com.marensovich.eljur.data.NotificationType;
import com.marensovich.eljur.exceptions.Exceptions.InvalidNotificationTypeException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
