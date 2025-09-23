package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * The type Students.
 */
@Entity
@Data
@Table(name = "students")
public class Students implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "students_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "students_FullName", nullable = false, unique = true)
    private String fullname;
    @Column(name = "students_groupID", nullable = false)
    private Integer group;
    @Column(name = "students_subgroup", nullable = false)
    private Integer subgroup;

}
