package com.marensovich.eljur.controller.v1.api.Mobile;

import java.util.Map;

import com.marensovich.eljur.exceptions.Exceptions.InvalidPasswordException;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        if (!password.equals(user.getPassword())) throw new InvalidPasswordException("Uncorrect password");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Авторизация успешна!"));
    }
}
