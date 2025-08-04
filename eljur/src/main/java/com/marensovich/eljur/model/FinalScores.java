package com.marensovich.eljur.model;

import com.marensovich.eljur.data.FinalScoresType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Year;

@Data
@Entity
@Table(name = "finalScores")
public class FinalScores implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userID", nullable = false)
    private Integer userID;

    @Column(name = "subjectID", nullable = false)
    private Integer subjectID;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private FinalScoresType result;

    @Column(name = "half", nullable = false)
    private Integer half;

    @Column(name = "year", nullable = false)
    private Year year;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public FinalScoresType getResult() {
        return result;
    }

    public void setResult(FinalScoresType result) {
        this.result = result;
    }

    public Integer getHalf() {
        return half;
    }

    public void setHalf(Integer half) {
        this.half = half;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year yearID) {
        this.year = yearID;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
