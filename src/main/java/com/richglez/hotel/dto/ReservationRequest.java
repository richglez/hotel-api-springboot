package com.richglez.hotel.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    @NotNull(message = "Check-in is required")
    @FutureOrPresent(message = "Check-in date must be today or future")
    private LocalDateTime checkIn;

    @NotNull(message = "Check-out is required")
    @FutureOrPresent(message = "Check-out date must be today or future")
    private LocalDateTime checkOut;

    @NotNull(message = "Client is required")
    private Long clientId;

    @NotNull(message = "Room is required")
    private Long roomId;
}
