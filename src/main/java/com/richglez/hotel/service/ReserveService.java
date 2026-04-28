package com.richglez.hotel.service;

import com.richglez.hotel.model.Reservation;
import com.richglez.hotel.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveService {

    private final ReservationRepository respository;

    public ReserveService(ReservationRepository respository) {
        this.respository = respository;
    }

    // Metodos
    public List<Reservation> getReservations() {
        return respository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        return respository.save(reservation);
    }
}
