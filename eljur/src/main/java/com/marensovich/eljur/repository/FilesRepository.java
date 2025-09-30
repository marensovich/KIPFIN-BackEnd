package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Files repository.
 */
@Repository
public interface FilesRepository extends JpaRepository<Files, String> {

    String getFilenameById(Integer id);

    List<String> getFilenamesByFilename(String filename);
}
