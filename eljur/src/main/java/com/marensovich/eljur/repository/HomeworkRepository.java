package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {

    Optional<Homework> findHomeworkByLessonID(Integer lessonID);

    @Query("SELECT w.fileID FROM Homework w WHERE w.lessonID = :lessonID")
    List<String> getHomeworkFilesByLessonID(Integer lessonID);
}

