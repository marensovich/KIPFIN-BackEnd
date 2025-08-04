package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Students, String> {

    @Query(value = "SELECT * FROM students WHERE students_id = :id", nativeQuery = true)
    Optional<Students> findById(Integer id);
    Students getById(Integer id);
    Optional<Students> getGroupByFullname(String fullname);
}
