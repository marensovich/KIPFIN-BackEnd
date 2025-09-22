package com.marensovich.eljur.controller.v1.api.Mobile;

import java.util.Map;
import com.marensovich.eljur.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.http.HttpServletRequest;

@RestController
@Controller("mobileRegController")
@RequestMapping("/api/v1/mobile/auth")
public class RegController {


    @Autowired
    private AuthService authService;


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String key, @RequestParam String login, @RequestParam String password, HttpServletRequest request) {

        authService.registrationUser(key, login, password, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Registration successful."));
    }


}
