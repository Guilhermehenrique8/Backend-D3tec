package com.d3tec.template.nomeDoSeuProjeto.controller.auth;

import com.d3tec.template.nomeDoSeuProjeto.service.ImageUploadService;
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
@RequestMapping("/admin/images")
@RequiredArgsConstructor
@Tag(name = "Upload de Imagens", description = "Endpoints para upload de imagens")
public class ImageUploadController {
    
    private final ImageUploadService imageUploadService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Enviar uma imagem e receber a URL pública dela")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = imageUploadService.upload(file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
