package com.richglez.hotel.reservations.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    @NotNull(message = "Check-in is required")
    private LocalDateTime checkIn;

    @NotNull(message = "Check-out is required")
    private LocalDateTime checkOut;

    @NotNull(message = "Client is required")
    private Long clientId;

    @NotNull(message = "Room is required")
    private Long roomId;

    @NotNull(message = "Number of adults is required")
    private Integer adults;

    private Integer children; // optional?
}
