package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Shedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The interface Schedule repository.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Shedule, Integer> {
    /**
     * Gets lessons by date between and group and subgroup.
     *
     * @param dateAfter  the date after
     * @param dateBefore the date before
     * @param group      the group
     * @param subgroup   the subgroup
     * @return the lessons by date between and group and subgroup
     */
    List<Shedule> getLessonsByDateBetweenAndGroupAndSubgroup(LocalDate dateAfter, LocalDate dateBefore, Integer group, Integer subgroup);
}