package com.grassehh.app.configuration

import com.grassehh.app.service.NonProxiedServiceImpl
import com.grassehh.app.service.ProxiedServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ServicesConfiguration {
    @Bean
    fun proxiedService(webClient: WebClient) = ProxiedServiceImpl(webClient)

    @Bean
    fun nonProxiedService(webClient: WebClient) = NonProxiedServiceImpl(webClient)
}