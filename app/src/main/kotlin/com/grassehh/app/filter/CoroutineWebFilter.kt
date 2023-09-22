package com.grassehh.app.filter

import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.ObservationRegistry
import kotlinx.coroutines.withContext
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange


class CoroutineWebFilter(private val observationRegistry: ObservationRegistry) : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        withContext(observationRegistry.asContextElement()) {
            chain.filter(exchange)
        }
    }
}
