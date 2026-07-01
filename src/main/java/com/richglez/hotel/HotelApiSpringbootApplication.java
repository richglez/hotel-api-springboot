package com.richglez.hotel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** Starts the Hotel API Spring Boot application. */
@OpenAPIDefinition(
        info = @Info(
                title = "Hotel Management API",
                version = "1.0",
                description = "API para gestion de reservaciones y habitaciones",
                contact = @Contact(name = "Rich", email = "rich@hotel.com")
        )
)
@SpringBootApplication
@EnableJpaAuditing
public class HotelApiSpringbootApplication {

    /** Runs the Spring Boot application. */
    public static void main(String[] args) {
        SpringApplication.run(HotelApiSpringbootApplication.class, args);
    }
}
