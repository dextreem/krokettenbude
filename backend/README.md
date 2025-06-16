# Developer Guide

## Concept and API Documentation

The concept can be found [here](../docs/concept/concept.md).
After starting the service, swagger is available at [http//localhost:8080/docs]()

## Dev Setup

The backend a regular Spring Boot application on Kotlin; it's recommended to use IDEA.
After cloning the repository, IDEA should be able to import the gradle project upon opening the folder.

## Enable LLM

To enable LLM croquette recommendation, set `spring.croqueteria.enable.llm=true`.
This requires a running `ollama` model on port `11434`. 
For demonstrating purposes, `deepseek-r1:1.5b` is used within the scope of this project.
See [LLM](../llm/README.md) for more information.

## Tests

From within IDEA, you can run the gradle Task `croquetteria/Tasks/verification/test`.

Via command line:
```bash
./gradlew test
```

The test task triggers multiple things:
* Unit tests
* Integration tests
* Jacoco test report

The report can then be found in `build/reports/jacoco/test/html/index.html`

## Build and run The Service

From within IDEA, you can run the gradle Task `croqueteria/Tasks/application/bootRun`.
Via Command line:

```bash
cd backend
./gradlew bootRun
```

To build the project, run `croqueteria/Tasks/build/bootJar`.
Via command line:

```bash
cd backend
./gradlew bootJar
```


## Docker Image

Building a docker image is the easiest way to bundle the application.

### Manually

Run these commands to build and bundle the project:

```bash
cd backend
./gradlew bootJar # Only required to make sure the backend is building successfully
podman build -t croqueteria-backend .
podman run -p 8080:8080 croqueteria-backend

```

### Using GitHub

[ci.ml](.github/workflows/ci.yml) provides a workflow that builds a docker image and uploads it as artifact to GitHub.

### Using Act

Install `act` following the [official guide](https://github.com/nektos/act), in my case:

```bash
yum -S act
```

Run build and test job: 
```bash
act --env BUILD_ENVIRONMENT=LOCAL --bind $(pwd):/github/workspace -j build-and-test -P ubuntu-latest=catthehacker/ubuntu:act-latest
```

Load the docker image:
```bash
podman load -i artifacts/image.tar
```

## Known limitations

* No pagination: The service always returns everything.
* LLM reasoning is slow and works only 50% of the time. Should be replaced by a proper external service. ChatGPT works fine.
* LLM is disabled for tests. This requires some effort to spin up the LLM and test properly.
* average rating is calculated for every request that is not cached. For now that's fine, but as soon as the croquette numbers will rise (and they will!) this can be a bottleneck
    * Solution: Update the average rating upon new ratings (event-driven?)
* Everybody can register a MANAGER user
