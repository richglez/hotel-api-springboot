package com.richglez.hotel.dto;

import com.richglez.hotel.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomRequest {
    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;


    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Availability is required")
    private Boolean available;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;

    @NotNull(message = "Size is required")
    @Positive(message = "Size must be positive")
    private Double size;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;
}
