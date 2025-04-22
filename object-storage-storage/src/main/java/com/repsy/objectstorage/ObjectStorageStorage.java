package com.repsy.objectstorage;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import java.io.InputStream;

public class ObjectStorageStorage {
    private final MinioClient minioClient;
    private final String bucket;

    public ObjectStorageStorage(String endpoint, String accessKey, String secretKey, String bucket) {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        this.bucket = bucket;
    }

    public void save(String packageName, String version, String fileName, InputStream inputStream, long size, String contentType) throws Exception {
        String objectName = packageName + "/" + version + "/" + fileName;
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .stream(inputStream, size, -1)
                .contentType(contentType)
                .build()
        );
    }

    public InputStream load(String packageName, String version, String fileName) throws Exception {
        String objectName = packageName + "/" + version + "/" + fileName;
        return minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build()
        );
    }
}
