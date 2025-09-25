package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Groups;
import com.marensovich.eljur.model.Shedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface Schedule repository.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Shedule, Integer> {
    List<Shedule> getLessonsByDateBetweenAndGroupAndSubgroupId(LocalDateTime dateAfter, LocalDateTime dateBefore, Groups group, Integer subgroupId);
}