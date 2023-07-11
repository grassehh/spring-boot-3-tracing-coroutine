package com.grassehh.app.configuration

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.brave.ReactorNettyHttpTracing
import reactor.netty.http.client.HttpClient

@Configuration
class ClientConfiguration {

    @Bean
    fun clientHttpConnector(reactorNettyHttpTracing: ReactorNettyHttpTracing) =
        ReactorClientHttpConnector(reactorNettyHttpTracing.decorateHttpClient(HttpClient.create()))

    @Bean
    fun webClientCustomizer(
        clientHttpConnector: ClientHttpConnector,
        reactorNettyHttpTracing: ReactorNettyHttpTracing
    ) = WebClientCustomizer {
        it.clientConnector(clientHttpConnector)
    }

    @Bean
    fun webClient(webClientBuilder: WebClient.Builder) = webClientBuilder.build()
}