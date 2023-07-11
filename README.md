# Spring Boot 3 Micrometer Tracing with Kotlin Coroutines
## Description
This project is an attempt of using [Micrometer Tracing](https://github.com/micrometer-metrics/tracing) combined with the following technologies:
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Kotlin coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Reactor Netty](https://projectreactor.io/docs/netty/release/reference/index.html)
- [Zipkin Brave](https://github.com/openzipkin/brave)

This might also be useful for those migrating *Spring Boot 2 & Spring Sleuth* to *Spring Boot 3* which has breaking changes about tracing.

# How to test the application
Run `./gradlew bootRun` then call the local server: `curl --location 'http://localhost:8080/test' --header 'Authorization: Basic dXNlcjp1c2Vy'`