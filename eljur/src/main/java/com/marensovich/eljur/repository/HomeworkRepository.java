package com.marensovich.eljur.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.marensovich.eljur.model.Homework;
import io.micrometer.common.KeyValues;
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


    List<Homework> findHomeworkByLessonId(Integer lessonId);

    List<Homework> getHomeworkByLessonId(Integer lessonId);
}

