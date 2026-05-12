package com.richglez.hotel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationResponse {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long client_id;
    private Long room_id;
}
