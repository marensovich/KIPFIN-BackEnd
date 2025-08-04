package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    Optional<Subject> findBySubjectName(String subjectName);
    Optional<Subject> findById(Integer id);

    @Query("SELECT s.subjectName FROM Subject s WHERE s.id = :id")
    String getSubjectNameById(@Param("id") Integer id);


    @Query("SELECT s.subjectName FROM Subject s WHERE s.id IN :subjectIds")
    List<String> getSubjectNamesByIds(List<Integer> subjectIds);

}