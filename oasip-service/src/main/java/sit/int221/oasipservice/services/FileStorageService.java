package sit.int221.oasipservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileStorageService {
    @Value("${upload.path}")
    private String uploadPath;

    public String uploadToFileSystem(MultipartFile file) throws IOException {
        String filePath = uploadPath + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        return filePath;
    }

    public byte[] downloadFromFileSystem(String filePath) throws IOException {
        byte[] files = Files.readAllBytes(new File(filePath).toPath());
        return files;
    }
}
