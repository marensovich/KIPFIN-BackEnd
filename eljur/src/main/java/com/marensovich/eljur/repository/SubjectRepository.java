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

    Subject getSubjectById(Integer id);

    @Query("SELECT s.name FROM Subject s WHERE s.id IN :ids")
    List<String> getSubjectNamesByIds(@Param("ids") List<Integer> ids);

    // ✅ Альтернатива: возвращать полные объекты Subject
    @Query("SELECT s FROM Subject s WHERE s.id IN :ids")
    List<Subject> getSubjectsByIds(@Param("ids") List<Integer> ids);

    // ✅ Если нужен один предмет по ID
    @Query("SELECT s.name FROM Subject s WHERE s.id = :id")
    String getSubjectNameById(@Param("id") Integer id);

    Optional<Subject> findByName(String name);
}