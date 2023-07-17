package com.grassehh.app.filter;

import io.micrometer.tracing.Tracer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class AppWebFilter implements WebFilter {
    private final Tracer tracer;

    public AppWebFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        tracer.createBaggageInScope("myBaggageFilter", UUID.randomUUID().toString());
        return chain.filter(exchange);
    }
}
