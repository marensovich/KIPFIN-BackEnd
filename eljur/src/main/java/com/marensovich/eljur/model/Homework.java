package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private Groups group;

    @Column(name = "subgroup", nullable = false)
    private Integer subgroup;

    @Column(name = "test", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "lessonId", nullable = false)
    private Integer lessonId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "homeworkIds")
    private List<Files> files = new ArrayList<>();

}