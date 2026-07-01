package com.richglez.hotel.reservations.repository;

import com.richglez.hotel.reservations.model.Reservation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository for reservation entities. */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /** Finds reservations that have not been soft-deleted. */
    List<Reservation> findAllByDeletedAtIsNull();

    /** Finds reservations that have been soft-deleted. */
    List<Reservation> findAllByDeletedAtIsNotNull();

    /** Finds reservations by user id. */
    Page<Reservation> findByUserId(Long userId, Pageable pageable);

    /** Finds reservations by room id. */
    Page<Reservation> findByRoomId(Long roomId, Pageable pageable);

    /** Finds reservations by user id and room id. */
    Page<Reservation> findByUserIdAndRoomId(Long userId, Long roomId, Pageable pageable);
}
