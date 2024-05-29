import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}
val springCloudVersion by extra("2023.0.1")

group = "org.app"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("org.springframework.boot:spring-boot-starter-validation")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
