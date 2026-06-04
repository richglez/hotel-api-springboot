package com.richglez.hotel.rooms.repository;

import com.richglez.hotel.common.enums.RoomType;
import com.richglez.hotel.rooms.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // SELECT * FROM room WHERE deleted_at IS NULL
    List<Room> findByDeletedAtIsNull();

    // available filter
    Page<Room> findByAvailable(Boolean available, Pageable pageable);

    // room type filter
    Page<Room> findByRoomType(RoomType roomType, Pageable pageable);

    // capacity filter
    Page<Room> findByCapacityGreaterThanEqual(Double capacity, Pageable pageable);

    // combined filters
    Page<Room> findByAvailableAndRoomType(
            Boolean available,
            RoomType roomType,
            Pageable pageable
    );

    // active rooms only filter
    List<Room> findByDeletedAtIsNotNull();

}
