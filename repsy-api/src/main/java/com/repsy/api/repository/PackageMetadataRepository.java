package com.repsy.api.repository;

import com.repsy.api.model.PackageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageMetadataRepository extends JpaRepository<PackageMetadata, Long> {
    PackageMetadata findByNameAndVersion(String name, String version);
}
