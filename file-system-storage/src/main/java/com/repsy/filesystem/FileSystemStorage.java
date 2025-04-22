package com.repsy.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemStorage {
    private final String baseDir;

    public FileSystemStorage(String baseDir) {
        this.baseDir = baseDir;
    }

    public void save(String packageName, String version, String fileName, InputStream inputStream) throws IOException {
        Path dir = Paths.get(baseDir, packageName, version);
        Files.createDirectories(dir);
        Path filePath = dir.resolve(fileName);
        Files.copy(inputStream, filePath);
    }

    public InputStream load(String packageName, String version, String fileName) throws IOException {
        Path filePath = Paths.get(baseDir, packageName, version, fileName);
        return Files.newInputStream(filePath);
    }
}
