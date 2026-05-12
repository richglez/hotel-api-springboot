package com.richglez.hotel.dto;

import com.richglez.hotel.model.RoomType;
import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private RoomType roomType;
    private Double price;
    private Boolean available;
}
