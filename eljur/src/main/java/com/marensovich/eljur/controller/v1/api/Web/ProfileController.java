package com.marensovich.eljur.controller.v1.api.Web;


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

/**
 * REST controller for managing user profiles.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Updating notification settings</li>
 *     <li>Changing profile images</li>
 *     <li>Fetching profile information</li>
 * </ul>
 *
 * Authentication is based on JWT tokens.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProfileService profileService;

    /**
     * Updates user notification settings.
     *
     * @param token                the JWT token of the user
     * @param notificationType     the type of notifications
     * @param notificationMessages whether to enable/disable message notifications
     * @param notificationHomework whether to enable/disable homework notifications
     * @param notificationScore    whether to enable/disable score notifications
     * @param notificationNews     whether to enable/disable news notifications
     * @return the response entity with success or error message
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
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

        if (user.isEmpty()) throw new UserNotFoundException("User not found");

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

    /**
     * Updates the profile image for the user.
     *
     * @param token    the JWT token of the user
     * @param filename the image file name
     * @return the response entity with success message
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @GetMapping("/setProfileImage")
    public ResponseEntity<?> setProfileImage(@RequestParam String token, String filename) {
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userID);

        if (user.isEmpty()) throw new UserNotFoundException("User not found");

        user.get().setProfileImage(filename);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Settings successfully applied"));
    }


    /**
     * Retrieves profile information of the user.
     *
     * @param token the JWT token of the user
     * @return the response entity containing profile info
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @GetMapping("/getProfileInfo")
    public ResponseEntity<?> profileInfo(@RequestParam String token) {
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userID);

        if (user.isEmpty()) throw new UserNotFoundException("User not found");

        Map profileInfo = profileService.getProfileInfo(user);

        return ResponseEntity.status(HttpStatus.OK).body(profileInfo);

    }
}

