plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.dextreem"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val mockitoAgent: Configuration by configurations.creating

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.jsonwebtoken:jjwt:0.12.6")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito:mockito-core:5.18.0")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Fix Mockito warning
	mockitoAgent("org.mockito:mockito-core:5.18.0") {
		isTransitive = false
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()

	// Fix Mockito warning
	jvmArgs("-javaagent:${mockitoAgent.asPath}")
}


tasks.register<Test>("unitTest") {
	description = "Runs unit tests."
	group = "verification"

	useJUnitPlatform()
	include("**/com/dextreem/croqueteria/unit/**")
	testClassesDirs = sourceSets["test"].output.classesDirs
	classpath = sourceSets["test"].runtimeClasspath

	jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

tasks.register<Test>("integrationTest") {
	description = "Runs integration tests."
	group = "verification"

	useJUnitPlatform()
	include("**/com/dextreem/croqueteria/integration/**")
	testClassesDirs = sourceSets["test"].output.classesDirs
	classpath = sourceSets["test"].runtimeClasspath

	jvmArgs("-javaagent:${mockitoAgent.asPath}")
}