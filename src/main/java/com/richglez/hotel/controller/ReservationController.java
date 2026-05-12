package com.richglez.hotel.controller;

import com.richglez.hotel.dto.ReservationResponse;
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
    public List<ReservationResponse> getReservations() {
        return service.getReservations();
    }

    @GetMapping("/{id}")
    public ReservationResponse getByID(@PathVariable Long id) {
        return service.getReservationById(id);
    }

    @PostMapping
    public ReservationResponse saveReservation(@RequestBody Reservation reservation) {
        return service.saveReservation(reservation);
    }

//    @PutMapping("/{id}")
//    public ReservationResponse updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
//        return service.updateReservation(id, reservation);
//    }
//
//    @DeleteMapping
//    public ReservationResponse deleteReservation(@PathVariable Long id) {
//        service.deleteReservation(id);
//    }
}
