package com.grassehh.app.configuration

import org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.brave.ReactorNettyHttpTracing

@Configuration
class ClientConfiguration {
    @Bean
    fun httpClientCustomizer(reactorNettyHttpTracing: ReactorNettyHttpTracing) = ReactorNettyHttpClientMapper {
//       reactorNettyHttpTracing.decorateHttpClient(it)
        it.metrics(true) { uriTagValue: String ->
            uriTagValue //Not sure what to do here ?
        }
    }

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