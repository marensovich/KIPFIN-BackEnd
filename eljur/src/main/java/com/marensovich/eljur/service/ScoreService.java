package com.marensovich.eljur.service;


import com.marensovich.eljur.data.DateOfHalfYear;
import com.marensovich.eljur.data.ScoreType;
import com.marensovich.eljur.data.ScoreWorkType;
import com.marensovich.eljur.model.FinalScores;
import com.marensovich.eljur.model.Score;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * The type Score service.
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private GroupsToSubjectRepository groupsToSubjectRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private FinalScoresRepository finalScoresRepository;


    /**
     * Get final scores map.
     *
     * @param user the user
     * @param year the year
     * @return the map
     */
    public Map<String, Object> getFinalScores(User user, int year){
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

        return response;
    }

    /**
     * Get scores map.
     *
     * @param user the user
     * @param half the half
     * @return the map
     */
    public Map<String, List<Map<String, Object>>> getScores(User user, int half){
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
                                    scoreDetails.put("score", convertScoreTypeToInt(score.getScoreType().toString()));
                                    scoreDetails.put("date", score.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                                    scoreDetails.put("comment", score.getScoreText());
                                    scoreDetails.put("workType", convertScoreWorkTypeToString(score.getScoreWork().toString()));
                                    return scoreDetails;
                                },
                                Collectors.toList()
                        )
                ));
        for (String subject : subjects) {
            scoresBySubject.putIfAbsent(subject, List.of());
        }
        return scoresBySubject;
    }

    /**
     * Gets avg score by user id.
     *
     * @param userID the user id
     * @return the avg score by user id
     */
    public Double getAvgScoreByUserID(Integer userID) {
        List<String> scoreStrings = scoreRepository.getScoreListByUserID(userID);

        if (scoreStrings.isEmpty()) {
            return 0.0;
        }
        List<Integer> scores = scoreStrings.stream()
                .map(this::convertScoreTypeToInt)
                .toList();

        OptionalDouble avg = scores.stream().mapToInt(Integer::intValue).average();

        if (avg.isEmpty()) {
            return 0.0;
        }
        double avgScore = avg.getAsDouble();
        for (ScoreType scoreType : ScoreType.values()) {
            if (avgScore == scoreType.getScore()) {
                return (double) scoreType.getScore();
            }
        }
        return avgScore;
    }

    /**
     * Gets avg score by subject.
     *
     * @param subjectID the subject id
     * @param userID    the user id
     * @return the avg score by subject
     */
    public Double getAvgScoreBySubject(Integer subjectID, Integer userID) {
        List<String> scoreStrings = scoreRepository.getScoreListByUserIDAndSubject(userID, subjectID);

        if (scoreStrings.isEmpty()) {
            return 0.0;
        }
        List<Integer> scores = scoreStrings.stream()
                .map(this::convertScoreTypeToInt)
                .toList();

        OptionalDouble avg = scores.stream().mapToInt(Integer::intValue).average();

        if (avg.isEmpty()) {
            return 0.0;
        }
        double avgScore = avg.getAsDouble();
        for (ScoreType scoreType : ScoreType.values()) {
            if (avgScore == scoreType.getScore()) {
                return (double) scoreType.getScore();
            }
        }
        return avgScore;
    }

    /**
     * Convert score type to int integer.
     *
     * @param scoreType the score type
     * @return the integer
     */
    public Integer convertScoreTypeToInt(String scoreType) {
        try {
            return ScoreType.valueOf(scoreType).getScore();
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    /**
     * Convert score work type to string string.
     *
     * @param workType the work type
     * @return the string
     */
    public String convertScoreWorkTypeToString(String workType) {
        try {
            return ScoreWorkType.valueOf(workType).getScoreWorkType();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

