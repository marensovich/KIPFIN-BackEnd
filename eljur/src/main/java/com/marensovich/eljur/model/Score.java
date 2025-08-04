package com.marensovich.eljur.model;

import com.marensovich.eljur.data.ScoreType;
import com.marensovich.eljur.data.ScoreWorkType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public ScoreWorkType getScoreWork() {
        return scoreWork;
    }

    public void setScoreWork(ScoreWorkType scoreWork) {
        this.scoreWork = scoreWork;
    }

    public String getScoreText() {
        return scoreText;
    }

    public void setScoreText(String scoreText) {
        this.scoreText = scoreText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getLessonID() {
        return lessonID;
    }

    public void setLessonID(Integer lessonID) {
        this.lessonID = lessonID;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }
}
