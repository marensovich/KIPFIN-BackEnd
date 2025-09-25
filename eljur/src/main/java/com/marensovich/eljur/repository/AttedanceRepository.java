package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Attedance repository.
 */
@Repository
public interface AttedanceRepository extends JpaRepository<Attendance, String> {

}
