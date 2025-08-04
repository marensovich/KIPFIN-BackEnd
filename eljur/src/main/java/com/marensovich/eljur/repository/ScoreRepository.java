package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {

    @Query("SELECT s.scoreType FROM Score s WHERE s.userID = :userID AND s.subjectID = :subjectID")
    List<String> getScoreListByUserIDAndSubject(Integer userID, Integer subjectID);

    @Query("SELECT s.scoreType FROM Score s WHERE s.userID = :userID")
    List<String> getScoreListByUserID(Integer userID);

    @Query("SELECT s.scoreType FROM Score s WHERE s.lessonID = :lessonID AND s.userID = :userID")
    List<String> getScoreTypeByLessonIDAndUserId(Integer lessonID, Integer userID);

    @Query("SELECT s.scoreText FROM Score s WHERE s.lessonID = :lessonID AND s.userID = :userID")
    List<String> getScoreTextByLessonIDAndUserId(Integer lessonID, Integer userID);

    @Query("SELECT s.scoreWork FROM Score s WHERE s.lessonID = :lessonID AND s.userID = :userID")
    List<String> getScoreWorkTypeByLessonIDAndUserId(Integer lessonID, Integer userID);

    @Query("SELECT s FROM Score s WHERE s.userID = :userID")
    List<Score> getAllByUserID(Integer userID);

    @Query("SELECT s FROM Score s WHERE s.userID = :userID AND s.date BETWEEN :start AND :end")
    List<Score> getAllByUserIDAndDateBetween(Integer userID, LocalDate start, LocalDate end);

}
