package com.richglez.hotel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponse {
    private Long id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long clientId;
    private Long roomId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deletedAt;
}
