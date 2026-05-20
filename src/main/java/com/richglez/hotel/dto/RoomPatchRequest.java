package com.richglez.hotel.dto;

import com.richglez.hotel.enums.RoomType;
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