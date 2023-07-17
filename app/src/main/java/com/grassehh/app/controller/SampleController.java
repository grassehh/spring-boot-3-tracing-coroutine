package com.grassehh.app.controller;

import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/test")
public class SampleController {
    private WebClient webClient;
    private Tracer tracer;

    private static Logger logger = LoggerFactory.getLogger(SampleController.class);

    public SampleController(WebClient webClient, Tracer tracer) {
        this.webClient = webClient;
        this.tracer = tracer;
    }

    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('USER')")
    public Mono<Void> test() {
        tracer.createBaggageInScope("myBaggageController", UUID.randomUUID().toString());
        logger.debug("Log from inside the controller. This should contain the baggage called myBaggageController");
        return webClient.get().uri(UriComponentsBuilder.newInstance().scheme("https").host("google.com").build().toUri()).retrieve().bodyToMono(String.class).then(Mono.empty());
    }
}