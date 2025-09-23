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

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    @Query(value = "SELECT * FROM students WHERE students_id = :id", nativeQuery = true)
    Optional<Students> findById(Integer id);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Students getById(Integer id);

    /**
     * Gets group by fullname.
     *
     * @param fullname the fullname
     * @return the group by fullname
     */
    Optional<Students> getGroupByFullname(String fullname);
}
