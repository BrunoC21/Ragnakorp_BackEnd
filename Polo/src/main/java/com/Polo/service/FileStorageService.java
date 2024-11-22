package com.Polo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final String IMAGE_DIR = "src/main/resources/static/images/";

    public String saveFile(MultipartFile file) throws IOException {
        // Crear un nombre único para el archivo
        String sanitizedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        Path imagePath = Paths.get(IMAGE_DIR, sanitizedFileName);

        // Crear directorios si no existen
        Files.createDirectories(imagePath.getParent());

        // Guardar archivo
        file.transferTo(imagePath.toFile());

        return sanitizedFileName; // Devolver el nombre del archivo guardado
    }

    public void deleteFile(String fileName) throws IOException {
        Path imagePath = Paths.get(IMAGE_DIR, fileName);
        Files.deleteIfExists(imagePath);
    }
}
