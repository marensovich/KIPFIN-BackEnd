package com.marensovich.eljur.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
public class StatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private double cpuUsage;
    private long usedMemory;
    private int visitCount;

    public StatRecord() {
    }

    public StatRecord(LocalDateTime timestamp, double cpuUsage, long usedMemory, int visitCount) {
        this.timestamp = timestamp;
        this.cpuUsage = cpuUsage;
        this.usedMemory = usedMemory;
        this.visitCount = visitCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public int getVisitCount() {
        return visitCount;
    }
}
