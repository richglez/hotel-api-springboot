package com.richglez.hotel.reservations.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "DTO for response reservation")
@Data
public class ReservationRequest {

    @Schema(description = "Check-in date", example = "2025-08-15")
    @NotNull(message = "Check-in is required")
    private LocalDateTime checkIn;

    @Schema(description = "Check-out date", example = "2025-08-20")
    @NotNull(message = "Check-out is required")
    private LocalDateTime checkOut;

    @Schema(description = "Client id", example = "1")
    @NotNull(message = "Client is required")
    private Long clientId;

    @Schema(description = "Room id", example = "1")
    @NotNull(message = "Room is required")
    private Long roomId;

    @Schema(description = "Quantity of adults", example = "1")
    @NotNull(message = "Number of adults is required")
    private Integer adults;

    @Schema(description = "Quantity of children", example = "0")
    private Integer children; // optional?

//    @Schema(description = "Estado de la reservación", example = "CONFIRMED",
//            allowableValues = {"PENDING", "CONFIRMED", "CANCELLED"})
//    private String status;
}
