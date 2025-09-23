package com.marensovich.eljur.controller.v1.api.Web;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.*;
import com.marensovich.eljur.repository.*;
import com.marensovich.eljur.service.SheduleService;
import com.marensovich.eljur.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * REST controller for managing lessons and schedules.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Fetching lessons for a user in a date range</li>
 * </ul>
 *
 * Visits are recorded automatically when schedule is retrieved.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/lessons")
public class ScheduleContoller {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private VisitService visitService;
    @Autowired private SheduleService sheduleService;
    @Autowired private UserRepository userRepository;


    /**
     * Retrieves user lessons for the given date range.
     *
     * @param token     the JWT token of the user
     * @param startDate the start date of the schedule
     * @param endDate   the end date of the schedule
     * @return the response entity containing grouped lessons
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
    @GetMapping("/getLessons")
    public ResponseEntity<?> getLessons(
            @RequestParam String token,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).get();
        if (user == null) throw new UserNotFoundException("User not found");

        visitService.recordVisit();

        try {
            TreeMap<String, TreeMap<Integer, Map<String, Object>>> groupedLessons = sheduleService.getLessons(user, startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(groupedLessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while getting schedule"));
        }
    }
}

