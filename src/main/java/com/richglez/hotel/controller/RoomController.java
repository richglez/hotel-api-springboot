package com.richglez.hotel.controller;

import com.richglez.hotel.dto.RoomRequest;
import com.richglez.hotel.dto.RoomResponse;
import com.richglez.hotel.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(request));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @PatchMapping("{id}")
    public ResponseEntity<RoomResponse> patchRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.patchRoom(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RoomResponse> softDeleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.softDeleteRoom(id));
    }

    @DeleteMapping("{id}/permanent")
    public ResponseEntity<RoomResponse> hardDeleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.hardDeleteRoom(id));
    }
}
