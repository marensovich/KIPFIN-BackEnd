package com.marensovich.eljur.model;

import com.marensovich.eljur.data.NotificationType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Integer id;


    @Column(name = "user_username", nullable = false, unique = true)
    private String username;
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;
    @Column(name = "user_password", nullable = false, unique = true)
    private String password;
    @Column(name = "user_regIP", nullable = false)
    private String regIP;
    @Column(name = "user_LastJoinIP", nullable = false)
    private String lastJoinIP;
    @Column(name = "user_FullName", nullable = false, unique = true)
    private String fullname;
    @Column(name = "user_phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "user_post", nullable = false)
    private String post;
    @Column(name = "user_groupID", nullable = true)
    private Integer group;
    @Column(name = "user_TelegramID", nullable = true, unique = true)
    private Long telegramID;
    @Column(name = "user_ProfileImage", nullable = true)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_notificationType", nullable = false)
    private NotificationType notificationType;

    @Column(name = "user_notificationHomework", nullable = false)
    private boolean notificationHomework;

    @Column(name = "user_notificationScore", nullable = false)
    private boolean notificationScore;

    @Column(name = "user_notificationNews", nullable = false)
    private boolean notificationNews;

    @Column(name = "user_notificationMessages", nullable = false)
    private boolean notificationMessages;

    @Column(name = "user_BlackTheme", nullable = false)
    private boolean black_theme;
}