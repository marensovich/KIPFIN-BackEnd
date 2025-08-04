package com.marensovich.eljur.controller.Web;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.InvalidPasswordException;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.exceptions.Handlers.AuthHandler;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        User user = userRepository.findByUsername(login);
        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        if (!password.equals(user.getPassword())) throw new InvalidPasswordException("Неверный пароль");

        String token = jwtUtil.generateToken(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Авторизация успешна!",
                "token", token
        ));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Выход успешно выполнен!"));
    }
}