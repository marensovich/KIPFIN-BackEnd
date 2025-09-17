package com.marensovich.eljur.model;


import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.checker.units.qual.Length;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "attendance")
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false, unique = true)
    private Integer id;


    @Column(name = "attendance_confirmed")
    private Boolean confirmed;
    @Column(name = "attendance_userID", nullable = false)
    private Integer userID;
    @Column(name = "attendance_time", nullable = false)
    private Timestamp time;

    @Column(name = "attendance_lessonID", nullable = false)
    private Integer lessonID;

}
