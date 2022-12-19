package sit.int221.oasipservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipservice.entities.Event;
import sit.int221.oasipservice.services.FileStorageService;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileStorageController {
    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFromFileSystem(@RequestParam String filePath) throws IOException {
        byte[] file = fileStorageService.downloadFromFileSystem(filePath);
        return ResponseEntity.status(HttpStatus.OK)
                .body(file);
    }
}
