package com.marensovich.eljur.service;


import com.marensovich.eljur.data.FilesDataType;
import com.marensovich.eljur.model.Files;
import com.marensovich.eljur.repository.FilesRepository;
import com.marensovich.eljur.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * The type File service.
 */
@Service
public class FileService {


    private final FilesRepository filesRepository;
    private final UserRepository userRepository;

    /**
     * Instantiates a new File service.
     *
     * @param filesRepository the files repository
     */
    public FileService(FilesRepository filesRepository, UserRepository userRepository) {
        this.filesRepository = filesRepository;
        this.userRepository = userRepository;
    }

    /**
     * Gets file name with id.
     *
     * @param IDs the ds
     * @return the file name with id
     */
    public Map<String, String> getFileNameWithID(String IDs) {
        Map<String, String> files = new HashMap<>();
        for (String ID : IDs.split(",")) {
            String fileName = filesRepository.getFilenameById(Integer.parseInt(ID.trim()));
            if (fileName != null) {
                files.put(ID, fileName);
            }
        }
        return files;
    }

    /**
     * Upload file files.
     *
     * @param userID   the user id
     * @param file     the file
     * @param fileType the file type
     * @return the files
     * @throws IOException the io exception
     */
    public Files uploadFile(Integer userID, MultipartFile file, String fileType) throws IOException {
        Files newFile = new Files();
        newFile.setUser(userRepository.getUserById(userID));
        newFile.setFileType(FilesDataType.valueOf(fileType));
        newFile.setFilename(file.getOriginalFilename());
        newFile.setFile(file.getBytes());

        filesRepository.save(newFile);
        return newFile;
    }



}
