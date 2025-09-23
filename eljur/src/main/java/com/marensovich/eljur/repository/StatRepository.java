package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.StatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Stat repository.
 */
@Repository
public interface StatRepository extends JpaRepository<StatRecord, Long> {

    /**
     * Find stats since list.
     *
     * @param startTime the start time
     * @return the list
     */
    @Query("SELECT s FROM StatRecord s WHERE s.timestamp >= :startTime ORDER BY s.timestamp ASC")
    List<StatRecord> findStatsSince(LocalDateTime startTime);
}
