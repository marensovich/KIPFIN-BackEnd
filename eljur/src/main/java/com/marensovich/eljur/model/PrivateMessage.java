package com.marensovich.eljur.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * The type Private message.
 */
@Data
@Entity
@Table(name = "private_messages")
public class PrivateMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "from_id", nullable = false)
    private Integer from_id;

    @Column(name = "to_id", nullable = true, unique = false)
    private String to_id;
    @Column(name = "date", nullable = true, unique = false)
    private LocalDate date;
    @Column(name = "message", nullable = true, unique = false)
    private String message;

}
