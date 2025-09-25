package com.marensovich.eljur.repository;


import com.marensovich.eljur.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * The interface Teacher repository.
 */
public interface TeacherRepository extends JpaRepository<Teacher,String> {

    Optional<Teacher> findTeacherById(Integer id);

    Integer getTeacherByUser_Id(Integer userId);
}
