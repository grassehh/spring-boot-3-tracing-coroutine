package com.grassehh.app.configuration;

import io.micrometer.context.ContextSnapshotFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.netty.LogbookServerHandler;

@Configuration
public class ServerConfiguration {
    @Bean
    public NettyServerCustomizer logbookNettyServerCustomizer(Logbook logbook, ContextSnapshotFactory contextSnapshotFactory) {
        return server -> server.doOnConnection(connection ->
                connection.addHandlerLast(
                        new TracingChannelDuplexHandler(
                                new LogbookServerHandler(logbook),
                                contextSnapshotFactory
                        )
                )
        ).metrics(true, (uriTagValue -> uriTagValue));
    }
}