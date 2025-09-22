package com.marensovich.eljur.service;


import com.marensovich.eljur.data.FilesDataType;
import com.marensovich.eljur.model.Files;
import com.marensovich.eljur.repository.FilesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileService {


    private final FilesRepository filesRepository;

    public FileService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public Map<String, String> getFileNameWithID(String IDs) {
        Map<String, String> files = new HashMap<>();
        for (String ID : IDs.split(",")) {
            String fileName = filesRepository.getFileNameById(ID.trim());
            if (fileName != null) {
                files.put(ID, fileName);
            }
        }
        return files;
    }

    public Files uploadFile(Integer userID, MultipartFile file, String fileType) throws IOException {
        Files newFile = new Files();
        newFile.setUserID(userID);
        newFile.setFiletype(FilesDataType.valueOf(fileType));
        newFile.setFilename(file.getOriginalFilename());
        newFile.setFile(file.getBytes());

        filesRepository.save(newFile);
        return newFile;
    }



}
