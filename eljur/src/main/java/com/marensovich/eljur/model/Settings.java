package com.marensovich.eljur.model;

import com.marensovich.eljur.data.NotificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "settings")
@Data
public class Settings {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "notificationType", nullable = false, length = 45)
    private NotificationType notificationType = NotificationType.None;

    @Column(name = "profileImage", length = 45)
    private String profileImage;

    @Column(name = "notificationHomework", nullable = false)
    private boolean notificationHomework = false;

    @Column(name = "notificationScore", nullable = false)
    private boolean notificationScore = false;

    @Column(name = "notificationNews", nullable = false)
    private boolean notificationNews = false;

    @Column(name = "notificationMessages", nullable = false)
    private boolean notificationMessages = false;
}