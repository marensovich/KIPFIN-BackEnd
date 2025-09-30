package com.marensovich.eljur.model;

import com.marensovich.eljur.data.system.PostTypes;
import com.marensovich.eljur.data.system.RegKeysStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "regKeys")
public class RegKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "`key`", nullable = false, length = 45)
    private String key;

    @Column(name = "fullname", nullable = false, length = 100)
    private String fullname;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 45)
    private RegKeysStatus status = RegKeysStatus.PENDING;

    @Column(name = "activateAt", nullable = false)
    private LocalDateTime activateAt;

    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "post", nullable = false, length = 45)
    private PostTypes post = PostTypes.student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`group`", nullable = true)
    private Groups group;

    @Column(name = "subgroup", nullable = true)
    private Integer subgroup;

    public boolean isExpired() {
        return activateAt.isBefore(LocalDateTime.now());
    }
}