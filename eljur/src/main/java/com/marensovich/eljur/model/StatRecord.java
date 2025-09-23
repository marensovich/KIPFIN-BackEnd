package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * The type Stat record.
 */
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

    /**
     * Instantiates a new Stat record.
     */
    public StatRecord() {
    }

    /**
     * Instantiates a new Stat record.
     *
     * @param timestamp  the timestamp
     * @param cpuUsage   the cpu usage
     * @param usedMemory the used memory
     * @param visitCount the visit count
     */
    public StatRecord(LocalDateTime timestamp, double cpuUsage, long usedMemory, int visitCount) {
        this.timestamp = timestamp;
        this.cpuUsage = cpuUsage;
        this.usedMemory = usedMemory;
        this.visitCount = visitCount;
    }
}
