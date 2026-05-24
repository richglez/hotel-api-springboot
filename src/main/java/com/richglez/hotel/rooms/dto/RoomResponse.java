package com.richglez.hotel.rooms.dto;

import com.richglez.hotel.common.enums.RoomType;
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
    private Integer capacity;
    private Double size;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
