package com.marensovich.eljur.controller.Web;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.marensovich.eljur.exceptions.Exceptions.FileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images/")
public class ImageController {

    private final String avatarPath = Paths.get("data/images/profileImages").toString();

    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("avatars/{fileName}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(avatarPath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());            
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException("Запрашиваемый файл не найден");
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}