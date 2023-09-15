package com.grassehh.app.controller

import io.micrometer.tracing.Tracer
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus.OK
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.util.*

@RestController
@RequestMapping("/test")
class SampleController(private val webClient: WebClient, private val tracer: Tracer) {
    companion object {
        private val logger = logger {}
    }

    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER')")
    suspend fun test() {
        tracer.createBaggageInScope("myBaggageController", UUID.randomUUID().toString())
        logger.debug { "Log from inside the controller. This should contain the baggage called myBaggageController" }
        webClient.get().uri { it.scheme("https").host("google.com").build() }
            .awaitExchange { }
    }
}