package com.richglez.hotel.controller;

import com.richglez.hotel.dto.ReservationRequest;
import com.richglez.hotel.dto.ReservationResponse;
import com.richglez.hotel.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ReservationResponse getReservationById(@PathVariable Long id) {
        return service.getReservationById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // solo ADMIN
    public ResponseEntity<ReservationResponse> saveReservation(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveReservation(request));
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
