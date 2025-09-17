package com.marensovich.eljur.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "admin")
public class Admins implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "admin_post", nullable = true, unique = false)
    private String post;
    @Column(name = "admin_photoLink", nullable = true, unique = false)
    private String photoLink;
    @Column(name = "admin_room", nullable = true, unique = false)
    private String room;
}