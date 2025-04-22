package com.repsy.api.storage;

import java.io.IOException;
import java.io.InputStream;

public interface StorageStrategy {
    void save(String packageName, String version, String fileName, InputStream inputStream, long size, String contentType) throws Exception;
    InputStream load(String packageName, String version, String fileName) throws Exception;
}
