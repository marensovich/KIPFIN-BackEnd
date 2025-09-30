package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Students repository.
 */
@Repository
public interface StudentsRepository extends JpaRepository<Students, String> {

    Students getStudentsById(Integer id);
}
