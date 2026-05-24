package com.richglez.hotel.rooms.dto;

import com.richglez.hotel.common.enums.RoomType;
import lombok.Data;

@Data
public class RoomPatchRequest {
    private String roomNumber;
    private RoomType roomType;
    private Double price;
    private Boolean available;
    private Integer capacity;
    private Double size;
    private String name;
    private String description;
}

// Campos opcionales para patch