package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "students")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Column(name = "fullname", nullable = false, length = 45)
    private String fullname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private Groups group;

    @Column(name = "subgroup", nullable = false)
    private Integer subgroup;

    // Обратные связи
    @OneToMany(mappedBy = "curator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Groups> curatedGroups = new ArrayList<>();

    @OneToMany(mappedBy = "leader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Groups> ledGroups = new ArrayList<>();
}