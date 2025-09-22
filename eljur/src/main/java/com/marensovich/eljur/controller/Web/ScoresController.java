package com.marensovich.eljur.controller.Web;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import com.marensovich.eljur.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/scores")
public class ScoresController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getScores")
    public ResponseEntity<?> getScores(
            @RequestParam String token,
            @RequestParam Integer half) {

        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).orElse(null);

        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        try {
            Map<String, List<Map<String, Object>>> scoresBySubject = scoreService.getScores(user, half);
            return ResponseEntity.status(HttpStatus.OK).body(scoresBySubject);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error occurred while retrieving scores"));
        }
    }

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getFinalScores")
    public ResponseEntity<?> getFinalScores(
            @RequestParam String token,
            @RequestParam Integer year) {
        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).orElse(null);
        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        try {
            Map<String, Object> response = scoreService.getFinalScores(user, year);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error occurred while retrieving final scores"));
        }
    }


}
