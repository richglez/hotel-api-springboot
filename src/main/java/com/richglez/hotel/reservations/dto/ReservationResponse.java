package com.richglez.hotel.reservations.dto;

import java.time.LocalDateTime;
import lombok.Data;

/** Reservation data returned by API endpoints. */
@Data
public class ReservationResponse {
    private Long id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long clientId;
    private Long roomId;
    private Integer adults;
    private Integer children;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deletedAt;
}
