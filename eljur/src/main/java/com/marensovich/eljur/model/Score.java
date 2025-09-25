package com.marensovich.eljur.model;

import com.marensovich.eljur.data.ScoreType;
import com.marensovich.eljur.data.ScoreWorkType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "lessonId", nullable = false)
    private Integer lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId", nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 45)
    private ScoreType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "workType", nullable = false, length = 45)
    private ScoreWorkType workType;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}