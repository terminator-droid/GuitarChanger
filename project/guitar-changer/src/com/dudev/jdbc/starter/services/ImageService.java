package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.util.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {

    private static final ImageService INSTANCE = new ImageService();

    private static final String basePath = PropertiesUtil.get("image.base.url");
    @SneakyThrows
    public void upload(String imagePath, InputStream imageContent) {
        Path fullPath = Path.of(basePath, imagePath);
        try (imageContent){
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }
    @SneakyThrows
    public Optional<InputStream> get(String imagePath) {
        Path imageFullPath = Path.of(basePath, imagePath);
        return Files.exists(imageFullPath)
                ? Optional.of(Files.newInputStream(imageFullPath))
                : Optional.empty();
    }

    public static ImageService getInstance() {
        return INSTANCE;
    }

}
