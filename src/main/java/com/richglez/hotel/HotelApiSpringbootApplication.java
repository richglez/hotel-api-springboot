package com.richglez.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // <-- esto activa @CreatedDate y @LastModifiedDate
public class HotelApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApiSpringbootApplication.class, args);
    }

}
