package com.richglez.hotel.rooms.repository;

import com.richglez.hotel.rooms.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long>{
    // SELECT * FROM room WHERE deleted_at IS NULL
    List<Room> findByDeletedAtIsNull();

    // SELECT * FROM room WHERE deleted_at IS NOT NULL
    List<Room> findByDeletedAtIsNotNull();
}
