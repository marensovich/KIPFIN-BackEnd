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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Timestamp activatedAt) {
        this.activatedAt = activatedAt;
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

    public Integer getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(Integer subgroup) {
        this.subgroup = subgroup;
    }
}