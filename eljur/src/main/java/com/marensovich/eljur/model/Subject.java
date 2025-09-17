package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectId", nullable = false, unique = true)
    private Integer id;

    @Column(name = "subjectName", nullable = false, unique = true)
    private String subjectName;
}
