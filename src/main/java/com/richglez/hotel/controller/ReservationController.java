package com.richglez.hotel.controller;

import com.richglez.hotel.dto.ReservationRequest;
import com.richglez.hotel.dto.ReservationResponse;
import com.richglez.hotel.service.ReserveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    //    @Autowired
    private final ReserveService service;

    public ReservationController(ReserveService service) {
        this.service = service;
    }

    // Metodos
    @GetMapping
    public List<ReservationResponse> getReservations() {
        return service.getReservations();
    }

    @GetMapping("/{id}")
    public ReservationResponse getByID(@PathVariable Long id) {
        return service.getReservationById(id);
    }

    @PostMapping
    public ReservationResponse saveReservation(@RequestBody ReservationRequest request) {
        return service.saveReservation(request);
    }

    @PutMapping("/{id}")
    public ReservationResponse updateReservation(@PathVariable Long id, @RequestBody ReservationRequest reservation) {
        return service.updateReservation(id, reservation);
    }

    @PatchMapping("/{id}")
    public ReservationResponse patchReservation(@PathVariable Long id, @RequestBody ReservationRequest reservation) {
        return service.patchReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public ReservationResponse softDeleteReservation(@PathVariable Long id) {
        return service.softDeleteReservation(id);
    }

    @DeleteMapping("/{id}/permanent")
    public ReservationResponse hardDeleteReservation(@PathVariable Long id) {
        return service.hardDeleteReservation(id);
    }
}
