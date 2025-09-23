package com.marensovich.eljur.repository;


import com.marensovich.eljur.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * The interface Teacher repository.
 */
public interface TeacherRepository extends JpaRepository<Teacher,String> {
    /**
     * Find teacher by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Teacher> findTeacherById(Integer id);

    /**
     * Gets group id.
     *
     * @param id the id
     * @return the group id
     */
    @Query("SELECT t.teacher_groupID FROM Teacher t WHERE t.id = :id")
    Integer getGroupId(Integer id);


}
