package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "`group`")
public class Groups implements Serializable {

    @Id
    @Column(name = "group_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String group;

    @Column(name = "group_curatorID", nullable = false, unique = true)
    private Integer curatorID;
    @Column(name = "group_HeadID", nullable = false, unique = true)
    private Integer HeadID;
    @Column(name = "group_teacherID", nullable = false, unique = true)
    private Integer teacherID;
}
