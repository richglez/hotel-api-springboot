package com.richglez.hotel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data // (POJOs) generando automáticamente getters, setters, etc
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;

    // nunca se devuelve password en un response por seguridad

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
