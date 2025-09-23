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
    @Query(value = "SELECT * FROM admin WHERE admin_id = :id", nativeQuery = true)
    Optional<Admins> findById(Integer id);

    /**
     * Gets admin post.
     *
     * @param id the id
     * @return the admin post
     */
    @Query(value = "SELECT admin_post FROM admin WHERE admin_id = :id", nativeQuery = true)
    String getAdminPost(Integer id);
}
