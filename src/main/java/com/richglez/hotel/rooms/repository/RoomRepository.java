package com.richglez.hotel.rooms.repository;

import com.richglez.hotel.common.enums.RoomType;
import com.richglez.hotel.rooms.model.Room;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository for room entities. */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /** Finds rooms that have not been soft-deleted. */
    List<Room> findByDeletedAtIsNull();

    /** Finds rooms by availability. */
    Page<Room> findByAvailable(Boolean available, Pageable pageable);

    /** Finds rooms by room type. */
    Page<Room> findByRoomType(RoomType roomType, Pageable pageable);

    /** Finds rooms by minimum capacity. */
    Page<Room> findByCapacityGreaterThanEqual(Double capacity, Pageable pageable);

    /** Finds rooms by availability and room type. */
    Page<Room> findByAvailableAndRoomType(
            Boolean available,
            RoomType roomType,
            Pageable pageable
    );

    /** Finds rooms that have been soft-deleted. */
    List<Room> findByDeletedAtIsNotNull();
}
