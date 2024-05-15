# Spring Boot 3 Micrometer Tracing with Kotlin Coroutines
## Description
This project is an attempt of using [Micrometer Tracing](https://github.com/micrometer-metrics/tracing) combined with the following technologies:
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Kotlin coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Reactor Netty](https://projectreactor.io/docs/netty/release/reference/index.html)
- [Zipkin Brave](https://github.com/openzipkin/brave)
- [Zalando Logbook](https://github.com/zalando/logbook)

This might also be useful for those migrating *Spring Boot 2 & Spring Sleuth* to *Spring Boot 3* which has breaking changes about tracing.<br/>

The aim is to manage to have standard baggage (traceId and spanId) and custom baggage (myBaggageFilter and myBaggageController) logged in every single log of the application.

Below are a list of issues related to this mechanism not working properly: 
- https://github.com/openzipkin/brave/issues/1376
- https://github.com/zalando/logbook/issues/1513
- https://github.com/zalando/logbook/issues/1513
- https://github.com/micrometer-metrics/tracing/issues/174

# How to test the application
Run `./gradlew bootRun` then call the local server depending on the behavior you want to test:
## Simple
This will test the following behavior:
1) Write a log in the controller
2) Make a `webClient` call

`curl --location 'http://localhost:8080/simple' --header 'Authorization: Basic dXNlcjp1c2Vy'`

## Suspend
This will test the following behavior:
1) Simulate some delay inside the coroutine so that kotlin suspends the function then resume it
2) Write a log in the controller
3) Make a `webClient` call

`curl --location 'http://localhost:8080/suspend' --header 'Authorization: Basic dXNlcjp1c2Vy'`

## Coroutine
This will test the following behavior:
1) Start a new coroutine
2) Write a log in the controller
3) Make a `webClient` call

## AOP
This will test the following behavior:
1) Start a new coroutine
2) Write a log in the controller
3) Make a `webClient` call through a proxied service

`curl --location 'http://localhost:8080/coroutine' --header 'Authorization: Basic dXNlcjp1c2Vy'`