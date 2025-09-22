package com.marensovich.eljur.controller.Web;


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

@RestController
@RequestMapping("/api/files")
public class FilesController {

    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FileService fileService;


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

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) {
        Optional<Files> fileOptional = filesRepository.findById(id);

        if (fileOptional.isEmpty()) throw new FileNotFoundException("Запрашиваемый файл не найден");

        Files file = fileOptional.get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE)
                .body(file.getFile());
    }
}