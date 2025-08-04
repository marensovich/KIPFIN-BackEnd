package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.GroupsToSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsToSubjectRepository extends JpaRepository<GroupsToSubjects, Integer> {

    @Query("SELECT g.subjectId FROM GroupsToSubjects g WHERE g.groupId = :groupId")
    List<Integer> getAllSubjectsByGroupId(Integer groupId);

}
