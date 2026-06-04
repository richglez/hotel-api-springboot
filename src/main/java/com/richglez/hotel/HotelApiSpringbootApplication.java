package com.richglez.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@OpenAPIDefinition(
        info = @Info(
                title = "Hotel Management API",
                version = "1.0",
                description = "API para gestión de reservaciones y habitaciones",
                contact = @Contact(name = "Rich", email = "rich@hotel.com")
        )
)

@SpringBootApplication
@EnableJpaAuditing // <-- esto activa @CreatedDate y @LastModifiedDate
public class HotelApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApiSpringbootApplication.class, args);
    }

}
