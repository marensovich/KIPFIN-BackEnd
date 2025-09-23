package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Files repository.
 */
@Repository
public interface FilesRepository extends JpaRepository<Files, String> {

    /**
     * Gets file name by id.
     *
     * @param id the id
     * @return the file name by id
     */
    @Query("SELECT f.filename FROM Files f WHERE f.id = :id")
    String getFileNameById(String id);


    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Files> findById(Integer id);
}
