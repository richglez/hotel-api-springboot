package com.richglez.hotel.controller;

import com.richglez.hotel.model.Reservation;
import com.richglez.hotel.repository.ReservationRepository;
import com.richglez.hotel.service.ReserveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReserveService service;

    public ReservationController(ReserveService service) {
        this.service = service;
    }

    // Metodos
    @GetMapping
    public List<Reservation> getReservations() {
        return service.getReservations();
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return service.saveReservation(reservation);
    }
}
