package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Homework repository.
 */
@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {

    /**
     * Find homework by lesson id optional.
     *
     * @param lessonID the lesson id
     * @return the optional
     */
    Optional<Homework> findHomeworkByLessonID(Integer lessonID);

    /**
     * Gets homework files by lesson id.
     *
     * @param lessonID the lesson id
     * @return the homework files by lesson id
     */
    @Query("SELECT w.fileID FROM Homework w WHERE w.lessonID = :lessonID")
    List<String> getHomeworkFilesByLessonID(Integer lessonID);
}

