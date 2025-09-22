package com.marensovich.eljur.controller.v1.api.Web;

import com.marensovich.eljur.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class RegController {

    @Autowired
    private AuthService authService;


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String key, @RequestParam String login, @RequestParam String password, HttpServletRequest request) {
        try {
            authService.registrationUser(key, login, password, request);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Вы успешно прошли регистрацию."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while registration user."));
        }
    }
}
