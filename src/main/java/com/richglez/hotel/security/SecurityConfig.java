package com.richglez.hotel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // OPTIONS para CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // AUTH público
                        .requestMatchers("/api/auth/**").permitAll()

                        // =========================
                        // ROOMS
                        // =========================

                        // TODOS los GET públicos
                        .requestMatchers(HttpMethod.GET, "/api/rooms/**").permitAll()

                        // Crear room -> ADMIN o MANAGER
                        .requestMatchers(HttpMethod.POST, "/api/rooms/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Actualizar room
                        .requestMatchers(HttpMethod.PUT, "/api/rooms/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // PATCH room
                        .requestMatchers(HttpMethod.PATCH, "/api/rooms/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // DELETE room
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**")
                        .hasRole("ADMIN")

                        // Otras rutas
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/manager/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers("/api/reception/**")
                        .hasAnyRole("ADMIN", "MANAGER", "RECEPTIONIST")

                        // cualquier otra petición requiere login
                        .anyRequest().authenticated()
                )

                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

// configurar: rutas públicas rutas protegidas filtros