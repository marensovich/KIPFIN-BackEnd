package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Shedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Shedule, Integer> {
    List<Shedule> getLessonsByDateBetweenAndGroupAndSubgroup(LocalDate dateAfter, LocalDate dateBefore, Integer group, Integer subgroup);
}