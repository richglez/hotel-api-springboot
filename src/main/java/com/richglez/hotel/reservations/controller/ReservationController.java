package com.richglez.hotel.reservations.controller;

import com.richglez.hotel.reservations.dto.ReservationRequest;
import com.richglez.hotel.reservations.dto.ReservationResponse;
import com.richglez.hotel.reservations.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.richglez.hotel.users.model.User;

import java.util.List;

@Tag(name = "Reservation", description = "Reservation Management")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    //    @Autowired
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // Metodos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public List<ReservationResponse> getReservations() {
        return service.getReservations();
    }

    @Operation(summary = "Get reservation with an ID")
    @Parameter(name = "id", description = "ID de la reservación", required = true)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ReservationResponse getReservationById(@PathVariable Long id) {
        return service.getReservationById(id);
    }


    @Operation(
            summary = "Create new reservation",
            description = "Create a new reservation for a guest with an available room"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservación creada exitosamente",
                    content = @Content(schema = @Schema(implementation = ReservationRequest.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')") // solo ADMIN
    // Que el backend obtenga el clientId solo (más seguro)
    public ResponseEntity<ReservationResponse> saveReservation(
            @Valid @RequestBody ReservationRequest request,
            @AuthenticationPrincipal UserDetails userDetails // ← Spring inyecta el usuario autenticado
    ) {
        // Sobreescribe el clientId con el del token, ignorando lo que mande el frontend
        User authenticatedUser = (User) userDetails;
        request.setClientId(authenticatedUser.getId()); // aqui sobreescribe el id sucio a el id real del cliente sin intervenir en el frontend

        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveReservation(request));
        // Instead of the frontend sending the clientId (which is a security risk — any user could manipulate it), the backend extracts it from the authenticated token:
    }


    @PutMapping("/{id}")
    public ReservationResponse updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationRequest reservation) {
        return service.updateReservation(id, reservation);
    }


    @PatchMapping("/{id}")
    public ReservationResponse patchReservation(@PathVariable Long id, @RequestBody ReservationRequest reservation) {
        return service.patchReservation(id, reservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> softDeleteReservation(@PathVariable Long id) {
        return ResponseEntity.ok(service.softDeleteReservation(id));
    }


    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> hardDeleteReservation(@PathVariable Long id) {
        service.hardDeleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
