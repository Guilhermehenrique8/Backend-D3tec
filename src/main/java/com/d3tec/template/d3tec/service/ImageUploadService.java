package com.d3tec.template.d3tec.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    private static final int MAX_WIDTH = 1200;
    private static final int MAX_HEIGHT = 1200;

    @Value("${storage.upload-dir:uploads}")
    private String uploadDir;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    private Path uploadRoot;

    private Path getUploadRoot() {
        if (uploadRoot == null) {
            uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        }
        return uploadRoot;
    }

    public String upload(MultipartFile file) {
        Path originalPath = null;
        try {
            Files.createDirectories(getUploadRoot());

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
            }

            String filename = UUID.randomUUID().toString();
            originalPath = getUploadRoot().resolve(filename + extension);
            file.transferTo(originalPath.toFile());

            BufferedImage image = ImageIO.read(originalPath.toFile());
            if (image == null) {
                throw new IOException("Formato de imagem nao suportado");
            }

            BufferedImage resized = resizeIfNeeded(image);

            Path webpPath = getUploadRoot().resolve(filename + ".webp");
            writeWebP(resized, webpPath.toFile());

            Files.deleteIfExists(originalPath);

            return contextPath + "/files/" + filename + ".webp";
        } catch (IOException e) {
            try { Files.deleteIfExists(originalPath); } catch (IOException ignored) {}
            throw new IllegalStateException("Falha ao salvar imagem", e);
        }
    }

    private BufferedImage resizeIfNeeded(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        if (w <= MAX_WIDTH && h <= MAX_HEIGHT) return image;

        double ratio = Math.min((double) MAX_WIDTH / w, (double) MAX_HEIGHT / h);
        int newW = (int) (w * ratio);
        int newH = (int) (h * ratio);

        BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newW, newH, null);
        g.dispose();
        return resized;
    }

    private void writeWebP(BufferedImage image, java.io.File output) throws IOException {
        var writers = ImageIO.getImageWritersByMIMEType("image/webp");
        if (!writers.hasNext()) {
            throw new IOException("Nenhum escritor WebP disponivel");
        }
        ImageWriter writer = writers.next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                String[] types = param.getCompressionTypes();
                if (types != null && types.length > 0) {
                    param.setCompressionType(types[0]);
                    param.setCompressionQuality(0.8f);
                }
            }
            writer.write(null, new IIOImage(image, null, null), param);
            ios.flush();
        } finally {
            writer.dispose();
        }
    }
}
