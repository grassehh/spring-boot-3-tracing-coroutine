package com.grassehh.app.configuration

import io.micrometer.context.ContextSnapshotFactory
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.Logbook
import org.zalando.logbook.netty.LogbookServerHandler
import reactor.netty.Connection

@Configuration
class ServerConfiguration {

    @Bean
    fun logbookNettyServerCustomizer(logbook: Logbook, contextSnapshotFactory: ContextSnapshotFactory) =
        NettyServerCustomizer { server ->
            server.doOnConnection { connection: Connection ->
                connection.addHandlerLast(
                    TracingChannelDuplexHandler(
                        LogbookServerHandler(logbook),
                        contextSnapshotFactory
                    )
                )
            }.metrics(true) { uriTagValue: String ->
                uriTagValue
            }
        }
}