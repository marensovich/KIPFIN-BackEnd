package com.marensovich.eljur.model;

import com.marensovich.eljur.data.AdminPosts;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admin")
public class Admins {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "post", length = 45)
    private AdminPosts post;

    @Column(name = "photoLink", length = 45)
    private String photoLink;

    @Column(name = "room", length = 45)
    private String room;
}