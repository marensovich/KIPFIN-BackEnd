package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "teacher_SubjectID", nullable = false)
    private Integer teacher_subjectID;
    @Column(name = "teacher_groupID", unique = true)
    private Integer teacher_groupID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacher_subject() {
        return teacher_subjectID;
    }

    public void setTeacher_subject(Integer teacher_subjectID) {
        this.teacher_subjectID = teacher_subjectID;
    }

    public Integer getTeacher_groupID() {
        return teacher_groupID;
    }

    public void setTeacher_groupID(Integer teacher_groupID) {
        this.teacher_groupID = teacher_groupID;
    }
}
