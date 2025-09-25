package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.GroupsToSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Groups to subject repository.
 */
@Repository
public interface GroupsToSubjectRepository extends JpaRepository<GroupsToSubjects, Integer> {

    List<Integer> getAllSubjectsByGroup_Id(Integer groupId);
}
