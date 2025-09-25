package com.marensovich.eljur.service;

import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.Settings;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.SettingsRepository;
import com.marensovich.eljur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    @Autowired private UserRepository userRepository;
    @Autowired private SettingsRepository settingsRepository;


    public Settings updateProfileImage(Integer userId, String filename) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Settings settings = user.getSettings();
        settings.setProfileImage(filename);

        return settingsRepository.save(settings);
    }
}
