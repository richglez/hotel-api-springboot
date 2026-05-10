package com.richglez.hotel.service;

import com.richglez.hotel.dto.RoomRequest;
import com.richglez.hotel.dto.RoomResponse;
import com.richglez.hotel.model.Room;
import com.richglez.hotel.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomResponse newRoom(Room room) {
        return toResponse(roomRepository.save(room));
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Metodo privado para logica interna
    private Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + id + " not found"));
    }

    //Metodo publico para controller peticiones endpoints
    public RoomResponse getRoomById(Long id) {
        return toResponse(findRoomById(id));
    }


    // helper response
    private RoomResponse toResponse(Room room) {
        RoomResponse response = new RoomResponse();

        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setAvailable(room.getAvailable());
        response.setPrice(room.getPrice());
        response.setRoomType(room.getRoomType());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());

        return response;
    }


}
