package com.marensovich.eljur.model;

import com.marensovich.eljur.data.system.FilesDataType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "FileType", nullable = false, length = 45)
    private FilesDataType fileType;

    @Column(name = "filename", nullable = false, length = 45)
    private String filename;

    @Lob
    @Column(name = "file", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] file;


}