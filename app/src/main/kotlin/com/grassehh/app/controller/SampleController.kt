package com.grassehh.app.controller

import com.grassehh.app.service.ProxiedService
import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.ObservationRegistry
import io.micrometer.tracing.Tracer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus.OK
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.util.*

@RestController
class SampleController(
    private val webClient: WebClient,
    private val tracer: Tracer,
    private val observationRegistry: ObservationRegistry,
    private val proxiedService: ProxiedService
) {
    companion object {
        private val logger = logger {}
    }

    @GetMapping("simple")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER')")
    suspend fun simple() {
        tracer.createBaggageInScope("myBaggageController", UUID.randomUUID().toString())
        logger.debug { "CONTROLLER LOG" }
        webClient.get()
            .uri("https://www.google.fr")
            .awaitExchange { }
    }

    @GetMapping("suspend")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER')")
    suspend fun suspend() {
        tracer.createBaggageInScope("myBaggageController", UUID.randomUUID().toString())
        delay(500)
        logger.debug { "CONTROLLER LOG" }
        webClient.get()
            .uri("https://www.google.fr")
            .awaitExchange { }
    }

    @GetMapping("coroutine")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER')")
    suspend fun coroutine() {
        tracer.createBaggageInScope("myBaggageController", UUID.randomUUID().toString())
        logger.debug { "CONTROLLER LOG BEFORE COROUTINE" }
        CoroutineScope(SupervisorJob()).launch(observationRegistry.asContextElement()) {
            logger.debug { "CONTROLLER LOG INSIDE COROUTINE" }
            webClient.get()
                .uri("https://www.google.fr")
                .awaitExchange { }
        }
    }

    @GetMapping("aop")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER')")
    suspend fun aop() {
        tracer.createBaggageInScope("myBaggageController", UUID.randomUUID().toString())
        logger.debug { "CONTROLLER LOG BEFORE COROUTINE" }
        CoroutineScope(SupervisorJob()).launch(observationRegistry.asContextElement()) {
            logger.debug { "CONTROLLER LOG INSIDE COROUTINE" }
            proxiedService.doSomethingSuspend()
        }
    }
}
