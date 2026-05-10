package com.richglez.hotel.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private String roomType;
    private Double price;
    private Boolean available;
}
