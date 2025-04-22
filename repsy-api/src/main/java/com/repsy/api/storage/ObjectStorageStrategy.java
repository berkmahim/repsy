package com.repsy.api.storage;

import com.repsy.objectstorage.ObjectStorageStorage;
import java.io.InputStream;

public class ObjectStorageStrategy implements StorageStrategy {
    private final ObjectStorageStorage storage;
    public ObjectStorageStrategy(String endpoint, String accessKey, String secretKey, String bucket) {
        this.storage = new ObjectStorageStorage(endpoint, accessKey, secretKey, bucket);
    }
    @Override
    public void save(String packageName, String version, String fileName, InputStream inputStream, long size, String contentType) throws Exception {
        storage.save(packageName, version, fileName, inputStream, size, contentType);
    }
    @Override
    public InputStream load(String packageName, String version, String fileName) throws Exception {
        return storage.load(packageName, version, fileName);
    }
}
