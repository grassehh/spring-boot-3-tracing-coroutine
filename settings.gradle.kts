rootProject.name = "spring-boot-3-tracing-coroutine"
include("app")
include("app:test")
findProject(":app:test")?.name = "test"
