package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Admin repostory.
 */
@Repository
public interface AdminRepostory extends JpaRepository<Admins, Integer> {

    Admins getAdminsById(Integer id);
}
