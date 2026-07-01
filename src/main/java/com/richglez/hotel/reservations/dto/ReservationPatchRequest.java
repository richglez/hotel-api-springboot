package com.richglez.hotel.reservations.dto;

import java.time.LocalDateTime;
import lombok.Data;

/** Optional fields for partially updating a reservation. */
@Data
public class ReservationPatchRequest {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long clientId;
    private Long roomId;
    private Integer adults;
    private Integer children;
}
