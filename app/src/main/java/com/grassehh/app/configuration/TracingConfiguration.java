package com.grassehh.app.configuration;

import io.micrometer.context.ContextSnapshot.Scope;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;
import reactor.netty.Metrics;

@Configuration
public class TracingConfiguration {
    private ObservationRegistry observationRegistry;

    public TracingConfiguration(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @Bean
    public ContextSnapshotFactory contextSnapshotFactory() {
        return ContextSnapshotFactory.builder().build();
    }

    @PostConstruct
    public void postConstruct() {
        Hooks.enableAutomaticContextPropagation();
//        ContextRegistry.getInstance().registerThreadLocalAccessor(ObservationAwareSpanThreadLocalAccessor(tracer));
        ObservationThreadLocalAccessor.getInstance().setObservationRegistry(observationRegistry);
        Metrics.observationRegistry(observationRegistry);
    }
}

class TracingChannelDuplexHandler extends ChannelDuplexHandler {
    private ChannelDuplexHandler delegate;
    private ContextSnapshotFactory contextSnapshotFactory;

    public TracingChannelDuplexHandler(ChannelDuplexHandler delegate, ContextSnapshotFactory contextSnapshotFactory) {
        this.delegate = delegate;
        this.contextSnapshotFactory = contextSnapshotFactory;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try (Scope scope = contextSnapshotFactory.setThreadLocalsFrom(ctx.channel())) {
            delegate.channelRead(ctx, msg);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        try (Scope scope = contextSnapshotFactory.setThreadLocalsFrom(ctx.channel())) {
            delegate.write(ctx, msg, promise);
        }
    }
}