package com.marensovich.eljur.model;

import com.marensovich.eljur.data.FilesDataType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * The type Files.
 */
@Data
@Table(name = "files")
@Entity
public class Files implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "userID", nullable = false)
    private Integer userID;

    @Enumerated(EnumType.STRING)
    @Column(name = "FileType", nullable = false)
    private FilesDataType filetype;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Lob
    @Column(name = "file", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] file;

}
