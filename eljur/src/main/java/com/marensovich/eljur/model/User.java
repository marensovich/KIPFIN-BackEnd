package com.marensovich.eljur.model;

import com.marensovich.eljur.data.PostTypes;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "regIP", nullable = false, length = 15)
    private String regIP;

    @Column(name = "lastIP", nullable = false, length = 15)
    private String lastIP;

    @Column(name = "fullname", nullable = false, length = 45)
    private String fullname;

    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "post", nullable = false, length = 45)
    private PostTypes post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Groups group;

    @Column(name = "telegramId")
    private Long telegramId;

    // Связи
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Settings settings;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Files> files = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendanceRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Score> scores = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Messages> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Messages> receivedMessages = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Students student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Teacher teacher;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Admins admin;

    @PrePersist
    public void createSettings() {
        if (this.settings == null) {
            this.settings = new Settings();
            this.settings.setUser(this);
        }
    }
}