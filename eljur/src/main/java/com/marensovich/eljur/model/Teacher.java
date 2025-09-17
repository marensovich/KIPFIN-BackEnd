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
}
