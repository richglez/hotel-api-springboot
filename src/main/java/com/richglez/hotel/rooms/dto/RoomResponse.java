package com.richglez.hotel.rooms.dto;

import com.richglez.hotel.common.enums.RoomType;
import java.time.LocalDateTime;
import lombok.Data;

/** Room data returned by API endpoints. */
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
