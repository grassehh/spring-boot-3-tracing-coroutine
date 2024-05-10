import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {
    id("org.springframework.boot") version "3.2.5"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

group = "com.grassehh"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/snapshot")
    }
}

dependencies {
    implementation(platform(BOM_COORDINATES))
    implementation(platform("org.zalando:logbook-bom:3.8.0"))
    implementation(platform("io.micrometer:micrometer-tracing-bom:1.2.5"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-tracing-bridge-brave") {
        exclude(group = "io.zipkin.reporter2", module = "zipkin-reporter-brave")
    }
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.zalando:logbook-spring-boot-starter")
    implementation("org.zalando:logbook-spring-boot-webflux-autoconfigure")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.8.0")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
