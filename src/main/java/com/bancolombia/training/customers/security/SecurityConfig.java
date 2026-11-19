package com.bancolombia.training.customers.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Starter: JWT activo y endpoints técnicos accesibles SOLO para el laboratorio local.
 *
 * TODO ALUMNO:
 * 1. Asignar ROLE_CUSTOMER_READ a GET /api/customers y GET /api/customers/{id}.
 * 2. Asignar ROLE_CUSTOMER_WRITE a POST/PUT/DELETE.
 * 3. Convertir realm_access.roles (Keycloak) a autoridades ROLE_*.
 * 4. Denegar explícitamente rutas restantes.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain customerSecurity(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // API stateless con JWT; sólo en este ejercicio
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.GET,
                    "/actuator/health",
                    "/actuator/health/**",
                    "/actuator/prometheus").permitAll()
                // TODO: Reglas por método y rol CUSTOMER_READ / CUSTOMER_WRITE.
                .anyExchange().authenticated()  // Temporal: reemplazar por reglas + denyAll.
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            // TODO: configurar JwtAuthenticationConverter para realm_access.roles.
            .build();
    }
}
