FROM docker.io/eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts settings.gradle.kts ./
COPY src src

RUN ./gradlew dependencies --no-daemon
RUN ./gradlew bootJar --no-daemon

FROM docker.io/eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
