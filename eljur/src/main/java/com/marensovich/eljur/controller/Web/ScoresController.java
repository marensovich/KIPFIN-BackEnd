package com.marensovich.eljur.controller.Web;


import com.marensovich.eljur.data.DateOfHalfYear;
import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.FinalScores;
import com.marensovich.eljur.model.Score;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import com.marensovich.eljur.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/scores")
public class ScoresController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private GroupsToSubjectRepository groupsToSubjectRepository;
    @Autowired
    private FinalScoresRepository finalScoresRepository;

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getScores")
    public ResponseEntity<?> getScores(
            @RequestParam String token,
            @RequestParam Integer half) {

        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).orElse(null);

        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        LocalDate startDate = null;
        LocalDate endDate = switch (half) {
            case 1 -> {
                startDate = DateOfHalfYear.FIRST.getStartDate();
                yield DateOfHalfYear.FIRST.getEndDate();
            }
            case 2 -> {
                startDate = DateOfHalfYear.SECOND.getStartDate();
                yield DateOfHalfYear.SECOND.getEndDate();
            }
            default -> throw new IllegalArgumentException("Неверное значение half: " + half);
        };

        Students student = studentsRepository.getById(user.getId());
        List<Integer> subjectIds = groupsToSubjectRepository.getAllSubjectsByGroupId(student.getGroup());
        List<String> subjects = subjectRepository.getSubjectNamesByIds(subjectIds);
        List<Score> scores;
        if (startDate != null && endDate != null) {
            scores = scoreRepository.getAllByUserIDAndDateBetween(user.getId(), startDate, endDate);
        } else {
            scores = scoreRepository.getAllByUserID(user.getId());
        }
        Map<String, List<Map<String, Object>>> scoresBySubject = scores.stream()
                .collect(Collectors.groupingBy(
                        score -> subjectRepository.getSubjectNameById(score.getSubjectID()),
                        Collectors.mapping(
                                score -> {
                                    Map<String, Object> scoreDetails = new HashMap<>();
                                    scoreDetails.put("score", scoreService.convertScoreTypeToInt(score.getScoreType().toString()));
                                    scoreDetails.put("date", score.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                                    scoreDetails.put("comment", score.getScoreText());
                                    scoreDetails.put("workType", scoreService.convertScoreWorkTypeToString(score.getScoreWork().toString()));
                                    return scoreDetails;
                                },
                                Collectors.toList()
                        )
                ));
        for (String subject : subjects) {
            scoresBySubject.putIfAbsent(subject, List.of());
        }
        return ResponseEntity.status(HttpStatus.OK).body(scoresBySubject);
    }

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getFinalScores")
    public ResponseEntity<?> getFinalScores(
            @RequestParam String token,
            @RequestParam Integer year) {
        User user = userRepository.findById(jwtUtil.getUserIdFromToken(token)).orElse(null);
        if (user == null) throw new UserNotFoundException("Пользователь не найден");

        List<FinalScores> finalScores = finalScoresRepository.findByUserIDAndYear(user.getId(), Year.of(year));
        Map<String, Map<String, Object>> result = new HashMap<>();
        Map<String, Double> averageScores = new HashMap<>();
        for (FinalScores score : finalScores) {
            String subjectName = subjectRepository.getSubjectNameById(score.getSubjectID());
            int half = score.getHalf();
            int numericValue = score.getResult().getNumericValue();
            result.putIfAbsent(subjectName, new HashMap<>());
            result.get(subjectName).put("half" + half, numericValue);
        }

        for (Map.Entry<String, Map<String, Object>> entry : result.entrySet()) {
            String subjectName = entry.getKey();
            Map<String, Object> subjectData = entry.getValue();

            Integer half1 = (Integer) subjectData.get("half1");
            Integer half2 = (Integer) subjectData.get("half2");
            double sum = 0;
            int count = 0;
            if (half1 != null) {
                sum += half1;
                count++;
            }
            if (half2 != null) {
                sum += half2;
                count++;
            }
            if (count > 0) {
                double average = sum / count;
                subjectData.put("average", average);
            } else {
                subjectData.put("average", null);
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("year", year);
        response.put("subjects", result);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
