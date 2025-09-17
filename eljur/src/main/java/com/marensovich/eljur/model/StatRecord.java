package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
public class StatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter private LocalDateTime timestamp;
    @Getter private double cpuUsage;
    @Getter private long usedMemory;
    @Getter private int visitCount;

    public StatRecord() {
    }

    public StatRecord(LocalDateTime timestamp, double cpuUsage, long usedMemory, int visitCount) {
        this.timestamp = timestamp;
        this.cpuUsage = cpuUsage;
        this.usedMemory = usedMemory;
        this.visitCount = visitCount;
    }
}
