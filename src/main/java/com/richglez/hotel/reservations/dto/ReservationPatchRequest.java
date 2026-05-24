package com.richglez.hotel.reservations.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationPatchRequest {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long clientId;
    private Long roomId;
}
