package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.FinalScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

/**
 * The interface Final scores repository.
 */
@Repository
public interface FinalScoresRepository extends JpaRepository<FinalScores, Integer> {

    /**
     * Find by user id and year list.
     *
     * @param userID the user id
     * @param year   the year
     * @return the list
     */
    @Query("SELECT f FROM FinalScores f WHERE f.userID = :userID AND f.year = :year")
    List<FinalScores> findByUserIDAndYear(Integer userID, Year year);
}
