package com.grassehh.app.configuration

import io.micrometer.context.ContextSnapshotFactory
import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor
import io.micrometer.tracing.Tracer
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Hooks
import reactor.netty.Metrics

@Configuration
class TracingConfiguration(private val observationRegistry: ObservationRegistry, private val tracer: Tracer) {
    @Bean
    fun contextSnapshotFactory() = ContextSnapshotFactory.builder().build()

    @PostConstruct
    fun postConstruct() {
        Hooks.enableAutomaticContextPropagation()
//        ContextRegistry.getInstance().registerThreadLocalAccessor(ObservationAwareSpanThreadLocalAccessor(tracer));
        ObservationThreadLocalAccessor.getInstance().observationRegistry = observationRegistry
        Metrics.observationRegistry(observationRegistry)
    }
}

class TracingChannelDuplexHandler(
    private val delegate: ChannelDuplexHandler,
    private val contextSnapshotFactory: ContextSnapshotFactory
) : ChannelDuplexHandler() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        contextSnapshotFactory.setThreadLocalsFrom<String>(ctx.channel()).use {
            delegate.channelRead(ctx, msg)
        }
    }

    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise?) {
        contextSnapshotFactory.setThreadLocalsFrom<String>(ctx.channel()).use {
            delegate.write(ctx, msg, promise)
        }
    }
}