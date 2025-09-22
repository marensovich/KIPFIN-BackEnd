package com.marensovich.eljur.controller.v1.api.Web;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import com.marensovich.eljur.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserContoller {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProfileService profileService;


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

        Map profileInfo = profileService.getProfileInfo(user);

        return ResponseEntity.status(HttpStatus.OK).body(profileInfo);
    }

}



