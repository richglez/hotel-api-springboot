package com.richglez.hotel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
//Variables de entorno ← gana siempre
    // Anotacion inyectar valor a esta variable de acuerdo al entorno en donde se encuentre
    @Value("${FRONTEND_URL:http://localhost:5173}") // Valor default
    private String frontendUrl;
    // "Busca la variable de entorno FRONTEND_URL. Si existe, úsala. Si NO existe, usa http://localhost:5173 como fallback."

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
