package com.grassehh.app.configuration

import brave.Tracing
import brave.http.HttpTracing
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Hooks
import reactor.netty.http.brave.ReactorNettyHttpTracing

@Configuration
class ServerConfiguration {
    init {
        Hooks.enableAutomaticContextPropagation()
    }

    @Bean
    fun tracing(tracing: Tracing): HttpTracing = HttpTracing.create(tracing)

    @Bean
    fun reactorNettyHttpTracing(httpTracing: HttpTracing) = ReactorNettyHttpTracing.create(httpTracing)

    @Bean
    fun nettyServerCustomizer(reactorNettyHttpTracing: ReactorNettyHttpTracing) = NettyServerCustomizer { server ->
        reactorNettyHttpTracing.decorateHttpServer(server)
    }
}