# 🖼️ Croqueteria UI

![UI screenshot](../imgs/frontend.png)

Frontend interface for the Croqueteria platform, mapping most backend functionalities into a clean, web-based experience.

---

## 🧩 Concept

The UI concept is outlined as part of the broader [project concept](../docs/concept/concept.md).

> ⚠️ The concept is intentionally high-level and loosely defined. The frontend mirrors the backend features closely, but not exhaustively (e.g., user management is currently limited).

---

## 🛠️ Build and Run Locally

This is a standard `npm` project.

### Development Mode:

```bash
npm install
npm run dev
```

### Production Build:

```bash
npm run build
```

---

## 📦 Docker Image

Containerizing the UI is the simplest way to bundle and distribute the frontend.

### 🧪 Manual Build

```bash
cd frontend
npm run build
podman build -t croqueteria-frontend .
podman run -p 80:80 croqueteria-frontend
```

### 🤖 GitHub CI

A Docker build workflow is defined in [`ci.yml`](.github/workflows/ci.yml), which automatically builds and uploads an image as a GitHub artifact.

### 🧰 Local CI with Act

If you want to test CI locally using [`act`](https://github.com/nektos/act):

```bash
yum -S act  # Or follow the official installation guide
```

Run the build job:

```bash
act --env BUILD_ENVIRONMENT=LOCAL --bind $(pwd):/github/workspace -j build-frontend -P ubuntu-latest=catthehacker/ubuntu:act-latest
```

Load the resulting image:

```bash
podman load -i artifacts/image.tar
```

---

## 🔄 Sync API Types

Whenever the backend API (Swagger/OpenAPI) changes, regenerate the frontend API client:

```bash
curl http://localhost:8080/v3/api-docs -o api-docs.json

openapi-generator generate -i ./api-docs.json -g typescript-fetch -o ./src/api-client

# Or using Docker:
podman run --rm -v ${PWD}:/local docker.io/openapitools/openapi-generator-cli generate \
  -i /local/api-docs.json \
  -g typescript-fetch \
  -o /local/src/api-client
```

---

## ⚠️ Known Limitations

- No frontend tests implemented
- Linting partially disabled due to auto-generated code
- Backend user management not yet integrated
- No mobile view
- User Guide missing
