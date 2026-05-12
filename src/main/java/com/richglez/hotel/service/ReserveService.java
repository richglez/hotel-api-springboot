package com.richglez.hotel.service;

import com.richglez.hotel.dto.ReservationResponse;
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
    public List<ReservationResponse> getReservations() {
        return respository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Reservation findReservationById(Long id) {
        return respository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    public ReservationResponse getReservationById(Long id) {
        return toResponse(findReservationById(id));
    }

    public ReservationResponse saveReservation(Reservation reservation) {
        return toResponse(respository.save(reservation));
    }

//    public Reservation updateReservation(long id, Reservation newReservation) {
//        return respository.findById(id).map(existingReservation -> {
//            existingReservation.setClientName(newReservation.getClientName());
//            existingReservation.setRoomName(newReservation.getRoomName());
//            existingReservation.setDate(newReservation.getDate());
//
//            return respository.save(existingReservation);
//        }).orElseThrow(() -> new RuntimeException("Reservation not found"));
//    }
//
//    public void deleteReservation(long id) {
//        var reservation = findReservationById(id);
//        respository.deleteById(id);
//    }

    public ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCheckIn(reservation.getCheckIn());
        response.setCheckOut(reservation.getCheckOut());
        response.setClient_id(reservation.getClient().getId());
        response.setRoom_id(reservation.getRoom().getId());

        return response;
    }


}
