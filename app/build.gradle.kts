import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {
    id("org.springframework.boot") version "3.2.0"
    kotlin("jvm") version "1.9.10"
}

group = "com.grassehh"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(BOM_COORDINATES))
    implementation(platform("org.zalando:logbook-bom:3.7.0"))
    implementation(platform("io.micrometer:micrometer-tracing-bom:1.2.0"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-tracing-bridge-brave") {
        exclude(group = "io.zipkin.reporter2", module = "zipkin-reporter-brave")
    }
    implementation("org.zalando:logbook-spring-boot-starter")
    implementation("org.zalando:logbook-spring-boot-webflux-autoconfigure")
    compileOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
