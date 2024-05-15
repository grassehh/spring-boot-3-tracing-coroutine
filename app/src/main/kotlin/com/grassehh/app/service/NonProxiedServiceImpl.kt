package com.grassehh.app.service

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange

class NonProxiedServiceImpl(private val webClient: WebClient) : NonProxiedService {
    override suspend fun doSomethingSuspend() {
        webClient.get()
            .uri("https://www.google.fr")
            .awaitExchange { }
    }
}