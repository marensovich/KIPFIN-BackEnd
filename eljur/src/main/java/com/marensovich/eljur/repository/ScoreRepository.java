package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Score;
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

    /**
     * Gets score list by user id and subject.
     *
     * @param userID    the user id
     * @param subjectID the subject id
     * @return the score list by user id and subject
     */
    @Query("SELECT s.scoreType FROM Score s WHERE s.userID = :userID AND s.subjectID = :subjectID")
    List<String> getScoreListByUserIDAndSubject(Integer userID, Integer subjectID);

    /**
     * Gets score list by user id.
     *
     * @param userID the user id
     * @return the score list by user id
     */
    @Query("SELECT s.scoreType FROM Score s WHERE s.userID = :userID")
    List<String> getScoreListByUserID(Integer userID);

    /**
     * Gets score type by lesson id and user id.
     *
     * @param lessonID the lesson id
     * @param userID   the user id
     * @return the score type by lesson id and user id
     */
    @Query("SELECT s.scoreType FROM Score s WHERE s.lessonID = :lessonID AND s.userID = :userID")
    List<String> getScoreTypeByLessonIDAndUserId(Integer lessonID, Integer userID);

    /**
     * Gets score text by lesson id and user id.
     *
     * @param lessonID the lesson id
     * @param userID   the user id
     * @return the score text by lesson id and user id
     */
    @Query("SELECT s.scoreText FROM Score s WHERE s.lessonID = :lessonID AND s.userID = :userID")
    List<String> getScoreTextByLessonIDAndUserId(Integer lessonID, Integer userID);

    /**
     * Gets score work type by lesson id and user id.
     *
     * @param lessonID the lesson id
     * @param userID   the user id
     * @return the score work type by lesson id and user id
     */
    @Query("SELECT s.scoreWork FROM Score s WHERE s.lessonID = :lessonID AND s.userID = :userID")
    List<String> getScoreWorkTypeByLessonIDAndUserId(Integer lessonID, Integer userID);

    /**
     * Gets all by user id.
     *
     * @param userID the user id
     * @return the all by user id
     */
    @Query("SELECT s FROM Score s WHERE s.userID = :userID")
    List<Score> getAllByUserID(Integer userID);

    /**
     * Gets all by user id and date between.
     *
     * @param userID the user id
     * @param start  the start
     * @param end    the end
     * @return the all by user id and date between
     */
    @Query("SELECT s FROM Score s WHERE s.userID = :userID AND s.date BETWEEN :start AND :end")
    List<Score> getAllByUserIDAndDateBetween(Integer userID, LocalDate start, LocalDate end);

}
