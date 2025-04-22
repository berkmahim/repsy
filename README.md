# Repsy Package Repository API

Minimal RESTful backend for a fictional package manager, built with Spring Boot.

---

## 🚀 Quick Overview
- **Upload & Download**: Versioned `.rep` (zip) and `meta.json` files
- **Pluggable Storage**: File-system & Object-storage (MinIO) strategy
- **PostgreSQL**: For fast metadata queries
- **Dockerized**: Easy deploy anywhere
- **Maven Private Repo**: All storage libraries are pulled from a private Maven repository (Repsy)

---

## 🔌 API Endpoints
- **POST** `/paketadi/1.0.0` → Upload package & meta (multipart/form-data)
- **GET** `/paketadi/1.0.0/meta.json` or `/paketadi/1.0.0/package.rep` → Download files
- **GET** `/metadata` → List all package metadata (JSON)

Example `meta.json`:
```json
{
  "name": "ornekpaket",
  "version": "1.0.0",
  "author": "John Doe",
  "dependencies": [
    { "package": "math", "version": "1.0.0" }
  ]
}
```

---

## 🛠️ Storage Strategy
- Set in `application.properties`:
  - `storageStrategy=file-system` **or** `object-storage`
- For MinIO:
  - `minio.endpoint`, `minio.accessKey`, `minio.secretKey`, `minio.bucket`

---

## ⚙️ Environment Variables
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
- `STORAGESTRATEGY`

---

## 🐳 Docker Usage
Build & run:
```bash
cd repsy-api
mvn clean package
# Build Docker image
docker build -t repo.repsy.io/brkmhm/testcase/repsy-api:latest .
# Push image
docker push repo.repsy.io/brkmhm/testcase/repsy-api:latest
# Run container
docker run -p 8080:8080 repo.repsy.io/brkmhm/testcase/repsy-api:latest
```

---

## 📦 Maven Integration (Private Repo)
- Both storage libraries are deployed to [Repsy Maven repo](https://repo.repsy.io/mvn/brkmhm/testcase)
- Main application's `pom.xml`:
```xml
<repositories>
  <repository>
    <id>repsy</id>
    <url>https://repo.repsy.io/mvn/brkmhm/testcase</url>
  </repository>
</repositories>
<dependency>
  <groupId>com.repsy</groupId>
  <artifactId>file-system-storage</artifactId>
  <version>1.0.0</version>
</dependency>
<dependency>
  <groupId>com.repsy</groupId>
  <artifactId>object-storage-storage</artifactId>
  <version>1.0.0</version>
</dependency>
```

---

## 🧪 Test & Documentation
- [Postman Collection](./Repsy-API-Postman-Collection.json) included
- Example `meta.json` in repo
- API can be tested via Postman or cURL

---

## 📝 Notes
- **Backend only** (no frontend, no auth)
- `.rep` file content is not inspected
- All business logic & storage strategy are extensible

---
