package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Groups repository.
 */
@Repository
public interface GroupsRepository extends JpaRepository<Groups, String> {

    Groups getGroupsById(Integer id);
}
