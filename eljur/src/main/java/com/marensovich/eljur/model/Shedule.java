package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "schedule")
public class Shedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "schedule_subjectID", nullable = false, unique = false)
    private Integer subject;
    @Column(name = "schedule_teacherID", nullable = false, unique = false)
    private Integer teacher;
    @Column(name = "schedule_room", nullable = false, unique = false)
    private String room;
    @Column(name = "schedule_groupID", nullable = false, unique = false)
    private Integer group;
    @Column(name = "schedule_subgroupID", nullable = true, unique = false)
    private Integer subgroup;
    @Column(name = "schedule_date", nullable = false, unique = false)
    private LocalDate date;
    @Column(name = "schedule_rank", nullable = false, unique = false)
    private Integer rank;

    @Column(name = "Time", nullable = false)
    private String time;

    public String getTimeStamp() {
        return time;
    }

    public void setTimeStamp(String time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getTeacher() {
        return teacher;
    }

    public void setTeacher(Integer teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(Integer subgroup) {
        this.subgroup = subgroup;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
