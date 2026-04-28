package com.richglez.hotel.controller;

import com.richglez.hotel.model.Reservation;
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
    public List<Reservation> getReservations() {
        return service.getReservations();
    }

    @GetMapping("/{id}")
    public Reservation getByID(@PathVariable Long id) {
        return service.findReservationById(id);
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return service.saveReservation(reservation);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return service.updateReservation(id, reservation);
    }

    @DeleteMapping
    public void deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
    }
}
