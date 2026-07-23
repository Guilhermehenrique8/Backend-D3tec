package com.d3tec.template.d3tec.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    public String upload(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(uploadDir));

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String filename = UUID.randomUUID() + extension;
            Path targetPath = Paths.get(uploadDir, filename);
            file.transferTo(targetPath.toFile());

            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao salvar imagem", e);
        }
    }
}
