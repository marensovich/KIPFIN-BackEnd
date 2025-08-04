package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.StatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<StatRecord, Long> {

    @Query("SELECT s FROM StatRecord s WHERE s.timestamp >= :startTime ORDER BY s.timestamp ASC")
    List<StatRecord> findStatsSince(LocalDateTime startTime);
}
