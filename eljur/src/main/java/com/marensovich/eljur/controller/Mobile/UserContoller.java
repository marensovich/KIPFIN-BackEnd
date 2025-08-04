package com.marensovich.eljur.controller.Mobile;


import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.Groups;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.GroupsRepository;
import com.marensovich.eljur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@Controller("mobileUserController")
@RequestMapping("/api/users/mobile")
public class UserContoller {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getIDbyUsername")
    public ResponseEntity<?> getIDbyUsername(@RequestParam String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id", user.getId()));
    }

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getAllInfo")
    public ResponseEntity<?> getAllUserInfo(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");

        Optional<Groups> group = groupsRepository.getGroupById(user.get().getGroup());

        return ResponseEntity.status(HttpStatus.OK).body(Map.ofEntries(
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
