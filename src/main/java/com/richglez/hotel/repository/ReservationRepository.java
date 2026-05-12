package com.richglez.hotel.repository;

import com.richglez.hotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // SELECT * FROM reservation WHERE deletedAt IS NULL
    List<Reservation> findAllByDeletedAtIsNull();

    // SELECT * FROM reservation WHERE deletedAt IS NOT NULL
    List<Reservation> findAllByDeletedAtIsNotNull();
}
