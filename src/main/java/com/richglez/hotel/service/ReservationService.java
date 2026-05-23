package com.richglez.hotel.service;

import com.richglez.hotel.dto.ReservationRequest;
import com.richglez.hotel.dto.ReservationResponse;
import com.richglez.hotel.model.Reservation;
import com.richglez.hotel.model.Room;
import com.richglez.hotel.model.User;
import com.richglez.hotel.repository.ReservationRepository;
import com.richglez.hotel.repository.RoomRepository;
import com.richglez.hotel.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    // Metodos
    public ReservationResponse saveReservation(ReservationRequest request) {
        // Validate a client and a room and asing to a variable the object
        User user = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (request.getCheckOut().isBefore(request.getCheckIn()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Check-in must be before check-out");

        // Next create a new reservation
        Reservation reservation = new Reservation();
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setUser(user);
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


    public ReservationResponse updateReservation(Long id, ReservationRequest request) {
        Reservation reservation = findReservationById(id);

        User user = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (request.getCheckOut().isBefore(request.getCheckIn()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Check-in must be before check-out");

        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setUser(user);
        reservation.setRoom(room);

        return toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse patchReservation(Long id, ReservationRequest request) {
        Reservation reservation = findReservationById(id);

        if (request.getCheckOut().isBefore(request.getCheckIn()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Check-in must be before check-out");

        if (request.getCheckIn() != null) {
            reservation.setCheckIn(request.getCheckIn());
        }
        if (request.getCheckOut() != null) {
            reservation.setCheckOut(request.getCheckOut());
        }
        if (request.getClientId() != null) {
            User user = userRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
            reservation.setUser(user);
        }
        if (request.getRoomId() != null) {
            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
            reservation.setRoom(room);
        }

        return toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponse softDeleteReservation(Long id) {
        Reservation reservation = findReservationById(id);

        reservation.setDeletedAt(LocalDateTime.now());
        return toResponse(reservationRepository.save(reservation));
    }

    public void hardDeleteReservation(long id) {
        Reservation reservation = findReservationById(id);

        reservationRepository.delete(reservation);
    }

    public ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCheckIn(reservation.getCheckIn());
        response.setCheckOut(reservation.getCheckOut());
        response.setCreateAt(reservation.getCreateAt());
        response.setUpdateAt(reservation.getUpdateAt());
        response.setDeletedAt(reservation.getDeletedAt());

        if (reservation.getUser() != null) {
            response.setClientId(reservation.getUser().getId());
        }
        if (reservation.getRoom() != null) {
            response.setRoomId(reservation.getRoom().getId());
        }


        return response;
    }


}
