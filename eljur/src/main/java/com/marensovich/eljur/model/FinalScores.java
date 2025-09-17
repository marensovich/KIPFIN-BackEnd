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

}
