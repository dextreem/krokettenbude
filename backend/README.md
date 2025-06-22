# 🛠️ Developer Guide

Comprehensive guide for contributing to or extending the Croqueteria backend service.

---

## 📘 Concept and API Documentation

- Project concept is available [here](../docs/concept/concept.md)
- After running the service, Swagger UI is available at: [http://localhost:8080/docs](http://localhost:8080/docs)

---

## 🧑‍💻 Development Setup

This is a Spring Boot application written in Kotlin. IntelliJ IDEA is the recommended IDE.

Steps:

1. Clone the repository
2. Open the project folder in IntelliJ IDEA
3. The Gradle project will be auto-imported

---

## 🧠 Enable LLM Support

To enable croquette recommendation via LLM:

- Add the following to your configuration:

```properties
spring.croqueteria.enable.llm=true
```

- Ensure an `ollama` instance is running on port `11434`
- This project uses `deepseek-r1:1.5b` as the default LLM (see [LLM module](../llm/README.md))

---

## ✅ Tests

### From IntelliJ IDEA

Run the Gradle task: `croqueteria > Tasks > verification > test`

### From CLI

```bash
./gradlew test
```

### Included in Test Task:

- Unit Tests
- Integration Tests
- Jacoco Coverage Report → `build/reports/jacoco/test/html/index.html`

---

## 🚀 Build and Run the Service

### From IntelliJ IDEA

Run the Gradle task: `croqueteria > Tasks > application > bootRun`

### From CLI

```bash
cd backend
./gradlew bootRun
```

### Build JAR

```bash
cd backend
./gradlew bootJar
```

---

## 📦 Docker Image

Containerizing the backend is recommended for deployment.

### 🧪 Manual Build

```bash
cd backend
./gradlew bootJar
podman build -t croqueteria-backend .
podman run -p 8080:8080 croqueteria-backend
```

### 🤖 GitHub CI

- See: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)
- Builds the backend and uploads the image as a GitHub artifact

### 🧰 Local CI with Act

Install `act` ([installation guide](https://github.com/nektos/act)):

```bash
yum -S act
```

Run the CI pipeline locally:

```bash
act --env BUILD_ENVIRONMENT=LOCAL --bind $(pwd):/github/workspace -j build-and-test-backend -P ubuntu-latest=catthehacker/ubuntu:act-latest
```

Load Docker image from artifact:

```bash
podman load -i artifacts/image.tar
```

---

## ⚠️ Known Limitations

- No pagination: All results are returned at once
- LLM inference is slow and only 50% reliable

  - Consider using an external API like ChatGPT

- LLM disabled in tests due to setup complexity
- Average rating is recalculated for every uncached request

  - Suggestion: Use event-driven updates for ratings

- Manager accounts can be freely registered
- JWT refresh tokens not implemented
