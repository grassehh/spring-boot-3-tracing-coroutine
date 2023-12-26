rootProject.name = "spring-boot-3-tracing-coroutine"
include("app")

pluginManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.spring.io/snapshot")
        }
    }
}