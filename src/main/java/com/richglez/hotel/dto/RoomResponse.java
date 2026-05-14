package com.richglez.hotel.dto;

import com.richglez.hotel.enums.RoomType;
import lombok.Data;

import java.time.LocalDateTime;

// Datos enregados al usuario
@Data
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private Double price;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
