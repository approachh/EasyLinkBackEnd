package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.port.out.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class FileStorageService implements FileStoragePort {


        @Value("${vibe.upload.dir}")
        private String uploadDir;

        private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "webp");

        @Override
        public String save(MultipartFile file) throws IOException {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new IllegalArgumentException("Missing file name");
            }

            String extension = getExtension(originalFilename);
            if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                throw new IllegalArgumentException("Unsupported file extension: " + extension);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed (got: " + contentType + ")");
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID() + "." + extension;
            Path destination = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Возвращаем URL для клиента
            return "/uploads/" + filename;
    }

        private String getExtension(String filename) {
            int dotIndex = filename.lastIndexOf('.');
            return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
        }
}
