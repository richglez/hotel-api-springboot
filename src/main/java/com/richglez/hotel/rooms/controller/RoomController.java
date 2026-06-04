package com.richglez.hotel.rooms.controller;

import com.richglez.hotel.common.enums.RoomType;
import com.richglez.hotel.reservations.dto.ReservationRequest;
import com.richglez.hotel.rooms.dto.RoomPatchRequest;
import com.richglez.hotel.rooms.dto.RoomRequest;
import com.richglez.hotel.rooms.dto.RoomResponse;
import com.richglez.hotel.rooms.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Room", description = "Room Management")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Operation(summary = "Create Room")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Room creada exitosamente",
                    content = @Content(schema = @Schema(implementation = ReservationRequest.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Room no encontrada")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // solo ADMIN puede crear habitaciones
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(request));
    }


    @Operation(summary = "Get All Rooms")
    @GetMapping
    public ResponseEntity<Page<RoomResponse>> getAllRooms(

            @RequestParam(required = false) Boolean available,

            @RequestParam(required = false) RoomType roomType,

            @RequestParam(required = false) Integer capacity,

            Pageable pageable
    ) {
        return ResponseEntity.ok(
                roomService.getAllRooms(
                        available,
                        roomType,
                        capacity,
                        pageable
                )
        );
    }


    @Operation(summary = "Get Room by id")
    @GetMapping("{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }


    @Operation(summary = "Update Room by id")
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }


    @Operation(summary = "Patch a Room by id")
    @PatchMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<RoomResponse> patchRoom(@PathVariable Long id, @RequestBody RoomPatchRequest request) {
        return ResponseEntity.ok(roomService.patchRoom(id, request));
    }


    @Operation(summary = "Soft delete Room")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RoomResponse> softDeleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.softDeleteRoom(id));
    }


    @Operation(summary = "Permanent delete Room")
    @DeleteMapping("{id}/permanent")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RoomResponse> hardDeleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.hardDeleteRoom(id));
    }
}
