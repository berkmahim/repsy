package com.repsy.api.storage;

import com.repsy.filesystem.FileSystemStorage;
import java.io.InputStream;

public class FileSystemStorageStrategy implements StorageStrategy {
    private final FileSystemStorage storage;
    public FileSystemStorageStrategy(String baseDir) {
        this.storage = new FileSystemStorage(baseDir);
    }
    @Override
    public void save(String packageName, String version, String fileName, InputStream inputStream, long size, String contentType) throws Exception {
        storage.save(packageName, version, fileName, inputStream);
    }
    @Override
    public InputStream load(String packageName, String version, String fileName) throws Exception {
        return storage.load(packageName, version, fileName);
    }
}
