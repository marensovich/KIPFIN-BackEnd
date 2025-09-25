package com.marensovich.eljur.controller.v1.api.Web;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.InvalidPasswordException;
import com.marensovich.eljur.exceptions.Exceptions.InvalidTokenException;
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
 * REST controller for user authentication and registration.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>User login {@code (/api/v1/auth/login) } </li>
 *     <li>User registration {@code (api/v1/auth/register) }</li>
 *     <li>User logout {@code (api/v1/auth/logout) } </li>
 * </ul>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
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
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param login    the username
     * @param password the password
     * @return the response entity with a JWT token or error message
     * @throws UserNotFoundException     if the user does not exist
     * @throws InvalidPasswordException  if the provided password is incorrect
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        User user = userRepository.findUserByUsername(login);
        if (user == null) throw new UserNotFoundException("User not found");

        if (!password.equals(user.getPassword())) throw new InvalidPasswordException("Incorrect password");

        String token = jwtUtil.generateToken(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Authorization Successful!",
                "token", token
        ));
    }

    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @PostMapping("/test")
    public ResponseEntity<?> test() {
        authService.test();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Test success!"
        ));
    }

    /**
     * Registers a new user with a provided key.
     *
     * @param key      the registration key
     * @param login    the desired username
     * @param password the desired password
     * @param request  the HTTP request
     * @return the response entity with a success or error message
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String key, @RequestParam String login, @RequestParam String password, HttpServletRequest request) {
        try {
            authService.registrationUser(key, login, password, request);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "You have successfully registered!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while registering user."));
        }
    }

    /**
     * Logs out the current user by clearing the authentication cookie.
     *
     * @param response the HTTP response
     * @return the response entity with a logout success message
     * @since v.0.1
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (token == null || token.isEmpty() || !jwtUtil.validateToken(token)) {
                        throw new InvalidTokenException("Token is empty or invalid");
                    }
                    cookie.setValue(null);
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return ResponseEntity.ok(Map.of("message", "Logout Successful!"));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "You are not logged in!"));
    }

}
