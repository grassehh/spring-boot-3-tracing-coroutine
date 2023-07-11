package com.grassehh.app.filter

import io.micrometer.tracing.Tracer
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


class AppWebFilter(private val tracer: Tracer) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        tracer.createBaggageInScope("myBaggageFilter", "myBaggageFilterValue")
        return chain.filter(exchange)
    }
}
