package com.richglez.hotel.reservations.controller;

import com.richglez.hotel.reservations.dto.ReservationRequest;
import com.richglez.hotel.reservations.dto.ReservationResponse;
import com.richglez.hotel.reservations.service.ReservationService;
import com.richglez.hotel.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Exposes reservation management endpoints. */
@Tag(name = "Reservation", description = "Reservation Management")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    /** Creates a reservation controller. */
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    /** Returns reservations using optional filters. */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public Page<ReservationResponse> getReservations(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long roomId,
            Pageable pageable) {
        return service.getReservations(clientId, roomId, pageable);
    }

    /** Returns a reservation by id. */
    @Operation(summary = "Get reservation with an ID")
    @Parameter(name = "id", description = "ID de la reservacion", required = true)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ReservationResponse getReservationById(@PathVariable Long id) {
        return service.getReservationById(id);
    }

    /** Creates a reservation for the authenticated user. */
    @Operation(
            summary = "Create new reservation",
            description = "Create a new reservation for a guest with an available room"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reservacion creada exitosamente",
                content = @Content(schema = @Schema(implementation = ReservationRequest.class))),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Habitacion no encontrada")
    })
    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(
            @Valid @RequestBody ReservationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        User authenticatedUser = (User) userDetails;
        request.setClientId(authenticatedUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveReservation(request));
    }

    /** Replaces a reservation by id. */
    @Operation(
            summary = "Update reservation",
            description = "Update a reservation for a guest"
    )
    @PutMapping("/{id}")
    public ReservationResponse updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest reservation) {
        return service.updateReservation(id, reservation);
    }

    /** Patches a reservation by id. */
    @Operation(
            summary = "Patch reservation",
            description = "Patch reservation for a guest"
    )
    @PatchMapping("/{id}")
    public ReservationResponse patchReservation(
            @PathVariable Long id,
            @RequestBody ReservationRequest reservation) {
        return service.patchReservation(id, reservation);
    }

    /** Soft deletes a reservation. */
    @Operation(
            summary = "Softdelete reservation",
            description = "Softdelete reservation for a guest"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> softDeleteReservation(@PathVariable Long id) {
        return ResponseEntity.ok(service.softDeleteReservation(id));
    }

    /** Permanently deletes a reservation. */
    @Operation(
            summary = "Permanent Delete reservation",
            description = "Permanent Delete reservation for a guest"
    )
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDeleteReservation(@PathVariable Long id) {
        service.hardDeleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
