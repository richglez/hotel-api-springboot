package com.richglez.hotel.service;

import com.richglez.hotel.dto.RoomPatchRequest;
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

    public RoomResponse addRoom(RoomRequest request) {
        Room room = new Room();

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        room.setAvailable(request.getAvailable());
        room.setCapacity(request.getCapacity());
        room.setSize(request.getSize());
        room.setName(request.getName());
        room.setDescription(request.getDescription());

        return toResponse(roomRepository.save(room));
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findByDeletedAtIsNull()
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

    public RoomResponse updateRoom(Long id, RoomRequest request) {
        Room room = findRoomById(id);

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        room.setAvailable(request.getAvailable());
        room.setCapacity(request.getCapacity());
        room.setSize(request.getSize());
        room.setName(request.getName());
        room.setDescription(request.getDescription());

        return toResponse(roomRepository.save(room));
    }

    public RoomResponse patchRoom(Long id, RoomPatchRequest roomRequest) {
        Room room = findRoomById(id);

        if (roomRequest.getRoomNumber() != null) room.setRoomNumber(roomRequest.getRoomNumber());
        if (roomRequest.getRoomType() != null) room.setRoomType(roomRequest.getRoomType());
        if (roomRequest.getPrice() != null) room.setPrice(roomRequest.getPrice());
        if (roomRequest.getAvailable() != null) room.setAvailable(roomRequest.getAvailable());
        if (roomRequest.getCapacity() != null) room.setCapacity(roomRequest.getCapacity());
        if (roomRequest.getSize() != null) room.setSize(roomRequest.getSize());
        if (roomRequest.getName() != null) room.setName(roomRequest.getName());
        if (roomRequest.getDescription() != null) room.setDescription(roomRequest.getDescription());

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
        response.setRoomType(room.getRoomType());
        response.setPrice(room.getPrice());
        response.setAvailable(room.getAvailable());
        response.setCapacity(room.getCapacity());
        response.setSize(room.getSize());
        response.setName(room.getName());
        response.setDescription(room.getDescription());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());
        response.setDeletedAt(room.getDeletedAt());

        return response;
    }

}
