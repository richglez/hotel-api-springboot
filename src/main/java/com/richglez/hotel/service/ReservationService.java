package com.richglez.hotel.service;

import com.richglez.hotel.dto.ReservationRequest;
import com.richglez.hotel.dto.ReservationResponse;
import com.richglez.hotel.model.Client;
import com.richglez.hotel.model.Reservation;
import com.richglez.hotel.model.Room;
import com.richglez.hotel.repository.ClientRepository;
import com.richglez.hotel.repository.ReservationRepository;
import com.richglez.hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
    }

    // Metodos
    public ReservationResponse saveReservation(ReservationRequest request) {
        // Validate a client and a room and asing to a variable the object
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        // Next create a new reservation
        Reservation reservation = new Reservation();
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setClient(client);
        reservation.setRoom(room);

        return toResponse(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // private for logic
    private Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    // public for controller
    public ReservationResponse getReservationById(Long id) {
        return toResponse(findReservationById(id));
    }


    public ReservationResponse updateReservation(long id, ReservationRequest request) {
        Reservation reservation = findReservationById(id);

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setClient(client);
        reservation.setRoom(room);

        return toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse patchReservation(long id, ReservationRequest request) {
        Reservation reservation = findReservationById(id);

        if (request.getCheckIn() != null) {
            reservation.setCheckIn(request.getCheckIn());
        }
        if (request.getCheckOut() != null) {
            reservation.setCheckOut(request.getCheckOut());
        }
        if (request.getClientId() != null) {
            Client client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
            reservation.setClient(client);
        }
        if (request.getRoomId() != null) {
            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
            reservation.setRoom(room);
        }

        return toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse softDeleteReservation(long id) {
        Reservation reservation = findReservationById(id);

        reservation.setDeletedAt(LocalDateTime.now());
        return toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse hardDeleteReservation(long id) {
        Reservation reservation = findReservationById(id);

        reservationRepository.delete(reservation);
        return toResponse(reservation);
    }

    public ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCheckIn(reservation.getCheckIn());
        response.setCheckOut(reservation.getCheckOut());
        response.setCreateAt(reservation.getCreateAt());
        response.setUpdateAt(reservation.getUpdateAt());
        response.setDeletedAt(reservation.getDeletedAt());

        if (reservation.getClient() != null) {
            response.setClientId(reservation.getClient().getId());
        }
        if (reservation.getRoom() != null) {
            response.setRoomId(reservation.getRoom().getId());
        }



        return response;
    }


}
