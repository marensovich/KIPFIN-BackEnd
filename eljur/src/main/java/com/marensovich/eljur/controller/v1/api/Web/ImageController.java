package com.marensovich.eljur.controller.v1.api.Web;


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

/**
 * REST controller for serving image files.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Loading user avatars</li>
 * </ul>
 *
 * Images are served from the directory {@code data/images/profileImages}.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/images/")
public class ImageController {

    private final String avatarPath = Paths.get("data/images/profileImages").toString();

    /**
     * Returns a user avatar by file name.
     *
     * @param fileName the name of the avatar file
     * @return the response entity containing the image resource
     * @throws FileNotFoundException if the file does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://202.181.188.160:25998", allowCredentials = "true")
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
                throw new FileNotFoundException("The requested file was not found");
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}