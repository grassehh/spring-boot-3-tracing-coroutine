package com.grassehh.app.configuration;

import com.grassehh.app.filter.AppWebFilter;
import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.HTTP_BASIC;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager = false, proxyTargetClass = true)
public class SecurityConfiguration {
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        return new MapReactiveUserDetailsService(User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build());
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, Tracer tracer) {
        return http.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .addFilterBefore(new AppWebFilter(tracer), HTTP_BASIC)
                .build();
    }
}