package com.marensovich.eljur.controller.v1.api.Mobile;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.InvalidJwtTokenFormat;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import com.marensovich.eljur.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@Controller("mobileUserController")
@RequestMapping("/api/v1/users/mobile")
public class UserContoller {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProfileService profileService;


    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @GetMapping("/getUsernameByToken")
    public ResponseEntity<?> getUsernameByToken(@RequestParam String token) {
        if (token.chars().filter(ch -> ch == '.').count() != 2) {
            throw new InvalidJwtTokenFormat("Invalid token format");
        }
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            String username = userOptional.get().getUsername();
            return ResponseEntity.ok().body(Map.of("username", username));
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @GetMapping("/getIDbyUsername")
    public ResponseEntity<?> getIDbyUsername(@RequestParam String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) throw new UserNotFoundException("User not found");

        return ResponseEntity.ok().body(Map.of("username", user.getUsername()));
    }

    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @GetMapping("/getAllInfo")
    public ResponseEntity<?> getAllUserInfo(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("User not found");

        Map profileInfo = profileService.getProfileInfo(user);

        return ResponseEntity.status(HttpStatus.OK).body(profileInfo);
    }
}
