package com.marensovich.eljur.controller.v1.api.Web;


import com.marensovich.eljur.exceptions.Exceptions.FileNotFoundException;
import com.marensovich.eljur.model.Files;
import com.marensovich.eljur.repository.FilesRepository;
import com.marensovich.eljur.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * REST controller for handling file operations.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Uploading files</li>
 *     <li>Downloading files by ID</li>
 * </ul>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/files")
public class FilesController {

    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FileService fileService;


    /**
     * Uploads a new file and stores it in the database.
     *
     * @param file     the uploaded file
     * @param userID   the ID of the user who uploads the file
     * @param fileType the type of the file
     * @return the response entity containing the file ID or error message
     * @since v.0.1
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userID") Integer userID,
            @RequestParam("fileType") String fileType
    ) {
        try {
            Files newFile = fileService.uploadFile(userID, file, fileType);

            return ResponseEntity.status(HttpStatus.OK).body("Файл успешно загружен с ID: " + newFile.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла");
        }
    }

    /**
     * Downloads a file by its ID.
     *
     * @param id the ID of the file
     * @return the response entity with the file as a byte array
     * @throws FileNotFoundException if the file does not exist
     * @since v.0.1
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) {
        Optional<Files> fileOptional = filesRepository.findById(id);

        if (fileOptional.isEmpty()) throw new FileNotFoundException("The requested file was not found");

        Files file = fileOptional.get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE)
                .body(file.getFile());
    }
}