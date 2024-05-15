package com.grassehh.app.service

import org.slf4j.MDC
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange

class ProxiedServiceImpl(private val webClient: WebClient) : ProxiedService {
    override suspend fun doSomethingSuspend() {
        webClient.get()
            .uri("https://www.google.fr")
            .awaitExchange {
                println(MDC.getCopyOfContextMap())
            }
    }
}