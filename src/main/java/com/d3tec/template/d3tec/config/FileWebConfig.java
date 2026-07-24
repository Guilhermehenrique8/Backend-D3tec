package com.d3tec.template.d3tec.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Slf4j
@Configuration
public class FileWebConfig implements WebMvcConfigurer {

    @Value("${storage.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolute = Paths.get(uploadDir).toAbsolutePath().toUri().toString();
        log.info("Servindo arquivos de: {}", absolute);
        registry.addResourceHandler("/files/**", "/api/files/**")
                .addResourceLocations(absolute);
    }
}
