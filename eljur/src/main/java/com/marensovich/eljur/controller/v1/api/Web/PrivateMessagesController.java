package com.marensovich.eljur.controller.v1.api.Web;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/pm/")
public class PrivateMessagesController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivateMessageService privateMessageService;


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("getUserPm")
    private ResponseEntity<?> getPM(@RequestParam String token) {
        Optional<User> user = userRepository.findById(jwtUtil.getUserIdFromToken(token));

        if (user.isEmpty()) throw new UserNotFoundException("User not found!");

        try {
            TreeMap<String, TreeMap<Integer, Map<String, Object>>> groupedMessages = privateMessageService.getPrivateMessages(user);
            return ResponseEntity.status(HttpStatus.OK).body(groupedMessages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while getting PM"));
        }

    }

}
