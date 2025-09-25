package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages") // Исправлено: было "private_messages"
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId", nullable = false)
    private User target;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
}