package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.service.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
@Tag(name = "Upload de Imagens (Blog)", description = "Upload de imagens para posts")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping(value = "/upload-cover", consumes = "multipart/form-data")
    @Operation(summary = "Enviar uma imagem de capa para post e receber a URL publica")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = imageUploadService.upload(file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
