package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Shedule.
 */
@Data
@Entity
@Table(name = "schedule")
public class Shedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "schedule_subjectID", nullable = false, unique = false)
    private Integer subject;
    @Column(name = "schedule_teacherID", nullable = false, unique = false)
    private Integer teacher;
    @Column(name = "schedule_room", nullable = false, unique = false)
    private String room;
    @Column(name = "schedule_groupID", nullable = false, unique = false)
    private Integer group;
    @Column(name = "schedule_subgroupID", nullable = true, unique = false)
    private Integer subgroup;
    @Column(name = "schedule_date", nullable = false, unique = false)
    private LocalDate date;
    @Column(name = "schedule_rank", nullable = false, unique = false)
    private Integer rank;

    @Column(name = "Time", nullable = false)
    private String time;

}
