package com.repsy.api.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageStrategyFactory {
    @Value("${storageStrategy:file-system}")
    private String strategy;
    @Value("${minio.endpoint:http:localhost:9000}")
    private String minioEndpoint;
    @Value("${minio.accessKey:minioadmin}")
    private String minioAccessKey;
    @Value("${minio.secretKey:minioadmin}")
    private String minioSecretKey;
    @Value("${minio.bucket:repsy}")
    private String minioBucket;
    @Bean
    public StorageStrategy storageStrategy() {
        if ("object-storage".equalsIgnoreCase(strategy)) {
            return new ObjectStorageStrategy(minioEndpoint, minioAccessKey, minioSecretKey, minioBucket);
        } else {
            return new FileSystemStorageStrategy("data");
        }
    }
}
