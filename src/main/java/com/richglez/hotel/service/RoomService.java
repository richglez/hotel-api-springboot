package com.richglez.hotel.service;

import com.richglez.hotel.dto.RoomRequest;
import com.richglez.hotel.dto.RoomResponse;
import com.richglez.hotel.model.Room;
import com.richglez.hotel.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomResponse addRoom(Room room) {
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

    public RoomResponse updateRoom(Long id, Room roomData) {
        Room room = findRoomById(id);

        room.setRoomNumber(roomData.getRoomNumber());
        room.setRoomType(roomData.getRoomType());
        room.setPrice(roomData.getPrice());
        room.setAvailable(roomData.getAvailable());

        return toResponse(roomRepository.save(room));
    }

    public RoomResponse patchRoom(Long id, RoomRequest roomRequest) {
        Room room = findRoomById(id);

        if (roomRequest.getRoomNumber() != null) room.setRoomNumber(roomRequest.getRoomNumber());
        if (roomRequest.getRoomType() != null) room.setRoomType(roomRequest.getRoomType());
        if (roomRequest.getPrice() != null) room.setPrice(roomRequest.getPrice());
        if (roomRequest.getAvailable() != null) room.setAvailable(roomRequest.getAvailable());

        return toResponse(roomRepository.save(room));
    }

    // soft delete
    public RoomResponse softDeleteRoom(Long id) {
        Room room = findRoomById(id);

        room.setDeletedAt(LocalDateTime.now());
        return toResponse(roomRepository.save(room));

    }

    // hard delete
    public RoomResponse hardDeleteRoom(Long id) {
        Room room = findRoomById(id);
        roomRepository.delete(room);

        return toResponse(room);
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
        response.setDeletedAt(room.getDeletedAt());

        return response;
    }

}
