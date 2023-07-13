package com.grassehh.app.configuration

import io.micrometer.context.ContextSnapshotFactory
import org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.zalando.logbook.Logbook
import org.zalando.logbook.netty.LogbookClientHandler
import reactor.netty.Connection

@Configuration
class ClientConfiguration {
    @Bean
    fun logbookNettyClientCustomizer(logbook: Logbook, contextSnapshotFactory: ContextSnapshotFactory) =
        ReactorNettyHttpClientMapper {
            it.doOnConnected { connection: Connection ->
                connection.addHandlerLast(
                    TracingChannelDuplexHandler(LogbookClientHandler(logbook), contextSnapshotFactory)
                )
            }
        }

    @Bean
    fun webClient(webClientBuilder: WebClient.Builder) = webClientBuilder.build()
}