package com.grassehh.app.configuration

import io.micrometer.context.ContextSnapshotFactory
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Hooks

@Configuration
class TracingConfiguration {
    init {
        Hooks.enableAutomaticContextPropagation()
    }

    @Bean
    fun contextSnapshotFactory() = ContextSnapshotFactory.builder().build()
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