package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Groups repository.
 */
@Repository
public interface GroupsRepository extends JpaRepository<Groups, String> {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Groups> findById(Integer id);

    /**
     * Gets group by id.
     *
     * @param id the id
     * @return the group by id
     */
    Optional<Groups> getGroupById(Integer id);

    /**
     * Gets group name by id.
     *
     * @param id the id
     * @return the group name by id
     */
    @Query(value = "SELECT group_name FROM `group` WHERE group_id = :id", nativeQuery = true)
    String getGroupNameByID(Integer id);
}
