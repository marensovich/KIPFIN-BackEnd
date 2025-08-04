package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, String> {
    Optional<Groups> findById(Integer id);

    Optional<Groups> getGroupById(Integer id);

    @Query(value = "SELECT group_name FROM `group` WHERE group_id = :id", nativeQuery = true)
    String getGroupNameByID(Integer id);
}
