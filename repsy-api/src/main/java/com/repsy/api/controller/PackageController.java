package com.repsy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.repsy.api.model.PackageMetadata;
import com.repsy.api.repository.PackageMetadataRepository;
import com.repsy.api.storage.StorageStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PackageController {
    @Autowired
    private StorageStrategy storageStrategy;
    @Autowired
    private PackageMetadataRepository metadataRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "{packageName}/{version}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPackage(
            @PathVariable String packageName,
            @PathVariable String version,
            @RequestParam("package.rep") MultipartFile packageRep,
            @RequestParam("meta.json") MultipartFile metaJson
    ) {
                PackageMetadata meta;
        try (InputStream metaStream = metaJson.getInputStream()) {
            meta = objectMapper.readValue(metaStream, PackageMetadata.class);
            if (meta.getName() == null || meta.getVersion() == null || meta.getAuthor() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("meta.json missing required fields");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid meta.json format");
        }
                metadataRepository.save(meta);
                try (InputStream repStream = packageRep.getInputStream()) {
            storageStrategy.save(packageName, version, "package.rep", repStream, packageRep.getSize(), packageRep.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File storage error (package.rep): " + e.getMessage());
        }
        try (InputStream metaStream = metaJson.getInputStream()) {
            storageStrategy.save(packageName, version, "meta.json", metaStream, metaJson.getSize(), metaJson.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File storage error (meta.json): " + e.getMessage());
        }
        return ResponseEntity.ok("Package uploaded successfully");
    }

    @GetMapping("{packageName}/{version}/{fileName}")
    public void downloadFile(
            @PathVariable String packageName,
            @PathVariable String version,
            @PathVariable String fileName,
            HttpServletResponse response
    ) throws IOException {
        try (InputStream is = storageStrategy.load(packageName, version, fileName)) {
            if (fileName.endsWith(".json")) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            } else if (fileName.endsWith(".rep")) {
                response.setContentType("application/zip");
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            is.transferTo(response.getOutputStream());
        } catch (Exception e) {
            response.sendError(404, "File not found");
        }
    }

    @GetMapping("/metadata")
    public ResponseEntity<?> listAllMetadata() {
        return ResponseEntity.ok(metadataRepository.findAll());
    }
}


