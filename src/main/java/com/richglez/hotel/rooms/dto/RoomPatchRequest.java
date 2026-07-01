package com.richglez.hotel.rooms.dto;

import com.richglez.hotel.common.enums.RoomType;
import lombok.Data;

/** Optional fields for partially updating a room. */
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
