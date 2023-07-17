package com.grassehh.app.configuration;

import io.micrometer.context.ContextSnapshotFactory;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.netty.LogbookClientHandler;

@Configuration
public class ClientConfiguration {
    @Bean
    public ReactorNettyHttpClientMapper logbookNettyClientCustomizer(Logbook logbook, ContextSnapshotFactory contextSnapshotFactory) {
        return httpClient -> httpClient.doOnConnected(connection ->
                connection.addHandlerLast(
                        new TracingChannelDuplexHandler(new LogbookClientHandler(logbook), contextSnapshotFactory)
                )
        );
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }
}