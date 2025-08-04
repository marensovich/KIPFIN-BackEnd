package com.marensovich.eljur.service;


import com.marensovich.eljur.data.ScoreType;
import com.marensovich.eljur.data.ScoreWorkType;
import com.marensovich.eljur.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

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

    public Integer convertScoreTypeToInt(String scoreType) {
        try {
            return ScoreType.valueOf(scoreType).getScore();
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    public String convertScoreWorkTypeToString(String workType) {
        try {
            return ScoreWorkType.valueOf(workType).getScoreWorkType();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

