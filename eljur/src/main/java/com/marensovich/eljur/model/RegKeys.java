package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "regKeys")
public class RegKeys implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regKeys_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "regKeys_key", nullable = false, unique = true)
    private String registrationKey;

    @Column(name = "regKeys_FullName", nullable = false, unique = true)
    private String fullName;

    @Column(name = "regKeys_Email", nullable = false, unique = true)
    private String email;

    @Column(name = "regKeys_Status", nullable = false)
    private String status;

    @Column(name = "regKeys_CreatedAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "regKeys_ActivatedAt")
    private Timestamp activatedAt;

    @Column(name = "regKeys_Phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "regKeys_Post", nullable = false, unique = false)
    private String post;

    @Column(name = "regKeys_group", nullable = true)
    private Integer group;

    @Column(name = "regKeys_subgroup", nullable = true)
    private Integer subgroup;

}