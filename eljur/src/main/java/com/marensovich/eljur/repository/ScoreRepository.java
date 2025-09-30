package com.marensovich.eljur.repository;

import com.marensovich.eljur.data.ScoreType;
import com.marensovich.eljur.model.Score;
import com.marensovich.eljur.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Score repository.
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {


    List<Score> getAllByUser(User user);

    List<Score> getAllByUserAndDateBetween(User user, LocalDate dateAfter, LocalDate dateBefore);

    List<ScoreType> getScoresByUser(User user);

    List<String> getScoresByUser_IdAndSubject_Id(Integer userId, Integer subjectId);

    List<String> getAllByLessonIdAndUser_Id(Integer lessonId, Integer userId);
}
