package com.pro.magistracy.tool;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class UploadFiles {

    public static void uploadFile(String name, MultipartFile file, String fileName) {
        String fileUrl = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if(fileUrl.isEmpty()) {
            return;
        }
        String uploadDir = "C:\\origin\\magistracy\\src\\main\\resources\\static\\upload_files\\" + name;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException ignored) {}
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(filePath);
        } catch (IOException ignored) {}
    }
}
