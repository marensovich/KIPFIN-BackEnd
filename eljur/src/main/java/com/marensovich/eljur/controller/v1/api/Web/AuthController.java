package com.marensovich.eljur.controller.v1.api.Web;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.InvalidPasswordException;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import com.marensovich.eljur.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Login response entity.
     *
     * @param login    the login
     * @param password the password
     * @return the response entity
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        User user = userRepository.findByUsername(login);
        if (user == null) throw new UserNotFoundException("User not found");

        if (!password.equals(user.getPassword())) throw new InvalidPasswordException("Incorrect password");

        String token = jwtUtil.generateToken(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Autorization Successful!",
                "token", token
        ));
    }

    /**
     * Register response entity.
     *
     * @param key      the key
     * @param login    the login
     * @param password the password
     * @param request  the request
     * @return the response entity
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String key, @RequestParam String login, @RequestParam String password, HttpServletRequest request) {
        try {
            authService.registrationUser(key, login, password, request);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "You have successfully registered!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while registration user."));
        }
    }

    /**
     * Logout response entity.
     *
     * @param response the response
     * @return the response entity
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Logout Successful!"));
    }
}