package com.marensovich.eljur.model;

import com.marensovich.eljur.data.ScoreType;
import com.marensovich.eljur.data.ScoreWorkType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Score.
 */
@Entity
@Data
@Table(name = "score")
public class Score implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scoreId", nullable = false, unique = true)
    private Integer id;

    @Column(name = "scoreUserID", nullable = false)
    private Integer userID;
    @Column(name = "scoreLessonID", nullable = false)
    private Integer lessonID;
    @Column(name = "scoreSubjectID", nullable = false)
    private Integer subjectID;

    @Enumerated(EnumType.STRING)
    @Column(name = "scoreType", nullable = false)
    private ScoreType scoreType;

    @Enumerated(EnumType.STRING)
    @Column(name = "scoreWork", nullable = false)
    private ScoreWorkType scoreWork;

    @Column(name = "scoreText")
    private String scoreText;

    @Column(name = "scoreDate", nullable = false)
    private LocalDate date;

}
