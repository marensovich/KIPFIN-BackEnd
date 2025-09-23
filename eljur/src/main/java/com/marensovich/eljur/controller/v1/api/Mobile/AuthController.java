package com.marensovich.eljur.controller.v1.api.Mobile;

import java.util.Map;

import com.marensovich.eljur.exceptions.Exceptions.InvalidPasswordException;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;

@RestController
@Controller("mobileAuthController")
@RequestMapping("/api/v1/mobile/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;


    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        User user = userRepository.getByUsernameMobile(login);

        if (user == null) throw new UserNotFoundException("User not found");
        if (!password.equals(user.getPassword())) throw new InvalidPasswordException("Incorrect password");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Авторизация успешна!"));
    }

    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String key, @RequestParam String login, @RequestParam String password, HttpServletRequest request) {
        authService.registrationUser(key, login, password, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Registration successful."));
    }
}
