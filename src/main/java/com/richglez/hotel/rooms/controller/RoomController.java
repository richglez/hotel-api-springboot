package com.richglez.hotel.rooms.controller;

import com.richglez.hotel.rooms.dto.RoomPatchRequest;
import com.richglez.hotel.rooms.dto.RoomRequest;
import com.richglez.hotel.rooms.dto.RoomResponse;
import com.richglez.hotel.rooms.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // solo ADMIN puede crear habitaciones
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
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<RoomResponse> patchRoom(@PathVariable Long id, @RequestBody RoomPatchRequest request) {
        return ResponseEntity.ok(roomService.patchRoom(id, request));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RoomResponse> softDeleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.softDeleteRoom(id));
    }

    @DeleteMapping("{id}/permanent")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RoomResponse> hardDeleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.hardDeleteRoom(id));
    }
}
