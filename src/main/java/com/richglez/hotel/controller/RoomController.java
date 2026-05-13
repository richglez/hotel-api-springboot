package com.richglez.hotel.controller;

import com.richglez.hotel.dto.RoomRequest;
import com.richglez.hotel.dto.RoomResponse;
import com.richglez.hotel.model.Room;
import com.richglez.hotel.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("{id}")
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public RoomResponse addRoom(@Valid @RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @PutMapping("{id}")
    public RoomResponse updateRoom(@PathVariable Long id, @Valid @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @PatchMapping("{id}")
    public RoomResponse patchRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
        return roomService.patchRoom(id, request);
    }

    @DeleteMapping("{id}")
    public RoomResponse softDeleteRoom(@PathVariable Long id) {
        return roomService.softDeleteRoom(id);
    }

    @DeleteMapping("{id}/permanent")
    public RoomResponse hardDeleteRoom(@PathVariable Long id) {
        return roomService.hardDeleteRoom(id);
    }
}
