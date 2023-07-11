package com.grassehh.app.configuration

import com.grassehh.app.filter.AppWebFilter
import io.micrometer.tracing.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder.HTTP_BASIC
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager = false, proxyTargetClass = true)
class SecurityConfiguration {
    @Bean
    fun userDetailsService() = MapReactiveUserDetailsService(
        User.withDefaultPasswordEncoder()
            .username("user")
            .password("user")
            .roles("USER")
            .build()
    )

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity, tracer: Tracer) =
        http
            .authorizeExchange { exchanges: AuthorizeExchangeSpec ->
                exchanges
                    .anyExchange().authenticated()
            }
            .httpBasic(withDefaults())
            .formLogin(withDefaults())
            .addFilterBefore(AppWebFilter(tracer), HTTP_BASIC)
            .build()
}