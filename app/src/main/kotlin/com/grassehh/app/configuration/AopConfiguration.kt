package com.grassehh.app.configuration

import io.micrometer.observation.ObservationRegistry
import org.aopalliance.intercept.MethodInterceptor
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AopConfiguration {

    @Bean
    fun autoProxyCreator() = BeanNameAutoProxyCreator().apply {
        setBeanNames("proxiedService")
        setInterceptorNames("dummyInterceptor")
    }

    @Bean
    fun dummyInterceptor(observationRegistry: ObservationRegistry) = MethodInterceptor { invocation -> invocation.proceed() }
}