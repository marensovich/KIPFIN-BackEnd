package com.marensovich.eljur.model;

import com.marensovich.eljur.data.FilesDataType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public FilesDataType getFiletype() {
        return filetype;
    }

    public void setFiletype(FilesDataType filetype) {
        this.filetype = filetype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
