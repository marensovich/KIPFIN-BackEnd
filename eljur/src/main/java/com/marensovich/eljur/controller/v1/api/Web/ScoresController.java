package com.marensovich.eljur.controller.v1.api.Web;


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


/**
 * REST controller for managing student scores.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Fetching scores for a specific half of the year</li>
 *     <li>Fetching final scores for a given year</li>
 * </ul>
 *
 * Requires JWT-based authentication.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/scores")
public class ScoresController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Retrieves scores for the specified half-year.
     *
     * @param token the JWT token of the user
     * @param half  the half of the academic year (e.g. 1 or 2)
     * @return the response entity containing scores grouped by subject
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getScores")
    public ResponseEntity<?> getScores(
            @RequestParam String token,
            @RequestParam Integer half) {

        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).orElse(null);

        if (user == null) throw new UserNotFoundException("User not found");

        try {
            Map<String, List<Map<String, Object>>> scoresBySubject = scoreService.getScores(user, half);
            return ResponseEntity.status(HttpStatus.OK).body(scoresBySubject);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error occurred while retrieving scores"));
        }
    }

    /**
     * Retrieves final scores for the specified year.
     *
     * @param token the JWT token of the user
     * @param year  the academic year
     * @return the response entity containing final scores
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getFinalScores")
    public ResponseEntity<?> getFinalScores(
            @RequestParam String token,
            @RequestParam Integer year) {
        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).orElse(null);
        if (user == null) throw new UserNotFoundException("User not found");

        try {
            Map<String, Object> response = scoreService.getFinalScores(user, year);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error occurred while retrieving final scores"));
        }
    }


}
