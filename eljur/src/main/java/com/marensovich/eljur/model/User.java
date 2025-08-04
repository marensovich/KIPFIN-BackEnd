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

    public boolean isBlack_theme() {
        return black_theme;
    }

    public void setBlack_theme(boolean black_theme) {
        this.black_theme = black_theme;
    }

    public boolean isNotificationHomework() {
        return notificationHomework;
    }

    public void setNotificationHomework(boolean notificationHomework) {
        this.notificationHomework = notificationHomework;
    }

    public boolean isNotificationScore() {
        return notificationScore;
    }

    public void setNotificationScore(boolean notificationScore) {
        this.notificationScore = notificationScore;
    }

    public boolean isNotificationNews() {
        return notificationNews;
    }

    public void setNotificationNews(boolean notificationNews) {
        this.notificationNews = notificationNews;
    }

    public boolean isNotificationMessages() {
        return notificationMessages;
    }

    public void setNotificationMessages(boolean notificationMessages) {
        this.notificationMessages = notificationMessages;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(Long telegramID) {
        this.telegramID = telegramID;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegIP() {
        return regIP;
    }

    public void setRegIP(String regIP) {
        this.regIP = regIP;
    }

    public String getLastJoinIP() {
        return lastJoinIP;
    }

    public void setLastJoinIP(String lastJoinIP) {
        this.lastJoinIP = lastJoinIP;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}