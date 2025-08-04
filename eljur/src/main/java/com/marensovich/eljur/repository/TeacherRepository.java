package com.marensovich.eljur.repository;


import com.marensovich.eljur.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,String> {
    Optional<Teacher> findTeacherById(Integer id);

    @Query("SELECT t.teacher_groupID FROM Teacher t WHERE t.id = :id")
    Integer getGroupId(Integer id);


}
