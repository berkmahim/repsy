package com.repsy.api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "package_metadata")
public class PackageMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String version;
    private String author;
    @ElementCollection
    @CollectionTable(name = "package_dependencies", joinColumns = @JoinColumn(name = "metadata_id"))
    private List<Dependency> dependencies;

        public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public List<Dependency> getDependencies() { return dependencies; }
    public void setDependencies(List<Dependency> dependencies) { this.dependencies = dependencies; }

    @Embeddable
    public static class Dependency {
        private String packageName;
        private String version;
        public String getPackage() { return packageName; }
        public void setPackage(String packageName) { this.packageName = packageName; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
    }
}
