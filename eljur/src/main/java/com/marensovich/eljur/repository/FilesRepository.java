package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<Files, String> {

    @Query("SELECT f.filename FROM Files f WHERE f.id = :id")
    String getFileNameById(String id);


    Optional<Files> findById(Integer id);
}
