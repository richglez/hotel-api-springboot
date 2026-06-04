package com.richglez.hotel.reservations.repository;

import com.richglez.hotel.reservations.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // SELECT * FROM reservation WHERE deletedAt IS NULL
    List<Reservation> findAllByDeletedAtIsNull();

    // SELECT * FROM reservation WHERE deletedAt IS NOT NULL
    List<Reservation> findAllByDeletedAtIsNotNull();

    // Filter by clientId
    Page<Reservation> findByUserId(Long userId, Pageable pageable);

    // Filter by roomId
    Page<Reservation> findByRoomId(Long roomId, Pageable pageable);

    // Filter by clientId and roomId
    Page<Reservation> findByUserIdAndRoomId(Long userId, Long roomId, Pageable pageable);

}
