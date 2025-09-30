package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.FinalScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

/**
 * The interface Final scores repository.
 */
@Repository
public interface FinalScoresRepository extends JpaRepository<FinalScores, Integer> {

    List<FinalScores> findByIdAndYear(Integer id, Year year);
}
