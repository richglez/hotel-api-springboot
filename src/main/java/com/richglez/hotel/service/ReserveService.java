package com.richglez.hotel.service;

import com.richglez.hotel.model.Reservation;
import com.richglez.hotel.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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

    public Reservation findReservationById(Long id) {
        return respository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    public Reservation saveReservation(Reservation reservation) {
        return respository.save(reservation);
    }

    public Reservation updateReservation(long id, Reservation newReservation) {
        return respository.findById(id).map(existingReservation -> {
            existingReservation.setClientName(newReservation.getClientName());
            existingReservation.setRoomName(newReservation.getRoomName());
            existingReservation.setDate(newReservation.getDate());

            return respository.save(existingReservation);
        }).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public void deleteReservation(long id) {
        var reservation = findReservationById(id);
        respository.deleteById(id);
    }


}
