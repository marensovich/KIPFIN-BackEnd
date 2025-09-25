package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "schedule")
public class Shedule implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

    @Column(name = "room", nullable = false, length = 45)
    private String room;

    @Column(name = "rank", nullable = false, length = 2)
    private Integer rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private Groups group;

    @Column(name = "subgroupId", nullable = false)
    private Integer subgroupId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "time", nullable = false, length = 45)
    private String time;

}