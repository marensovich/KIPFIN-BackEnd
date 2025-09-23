package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Subject repository.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    /**
     * Find by subject name optional.
     *
     * @param subjectName the subject name
     * @return the optional
     */
    Optional<Subject> findBySubjectName(String subjectName);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Subject> findById(Integer id);

    /**
     * Gets subject name by id.
     *
     * @param id the id
     * @return the subject name by id
     */
    @Query("SELECT s.subjectName FROM Subject s WHERE s.id = :id")
    String getSubjectNameById(@Param("id") Integer id);


    /**
     * Gets subject names by ids.
     *
     * @param subjectIds the subject ids
     * @return the subject names by ids
     */
    @Query("SELECT s.subjectName FROM Subject s WHERE s.id IN :subjectIds")
    List<String> getSubjectNamesByIds(List<Integer> subjectIds);

}