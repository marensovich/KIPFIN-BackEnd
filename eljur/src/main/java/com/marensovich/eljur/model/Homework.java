package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

/**
 * The type Homework.
 */
@Data
@Entity
@Table(name = "homework")
public class Homework implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homework_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "homework_groupID", nullable = false)
    private Integer groupID;

    @Column(name = "homework_subgroup")
    private Integer subgroup;

    @Column(name = "homework_text", nullable = false)
    private String homework;

    @Column(name = "homework_lessonID", nullable = false)
    private Integer lessonID;

    @Column(name = "ListFileID")
    private String fileID;

}
