package com.marensovich.eljur.service;


import com.marensovich.eljur.model.Admins;
import com.marensovich.eljur.repository.FilesRepository;
import org.springframework.stereotype.Service;

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



}
