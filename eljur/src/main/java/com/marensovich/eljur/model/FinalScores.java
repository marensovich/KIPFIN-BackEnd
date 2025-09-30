package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Year;

@Data
@Entity
@Table(name = "finalScores")
public class FinalScores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectID", nullable = false)
    private Subject subject;

    @Column(name = "result", nullable = false, length = 45)
    private String result;

    @Column(name = "half", nullable = false)
    private Integer half;

    @Column(name = "year", nullable = false, columnDefinition = "YEAR")
    private Integer year;
}