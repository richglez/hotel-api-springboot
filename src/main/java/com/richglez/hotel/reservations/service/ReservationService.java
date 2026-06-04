package com.richglez.hotel.reservations.service;

import com.richglez.hotel.reservations.dto.ReservationRequest;
import com.richglez.hotel.reservations.dto.ReservationResponse;
import com.richglez.hotel.reservations.model.Reservation;
import com.richglez.hotel.rooms.model.Room;
import com.richglez.hotel.users.model.User;
import com.richglez.hotel.reservations.repository.ReservationRepository;
import com.richglez.hotel.rooms.repository.RoomRepository;
import com.richglez.hotel.users.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        reservation.setAdults(request.getAdults());
        reservation.setChildren(request.getChildren());

        return toResponse(reservationRepository.save(reservation));
    }

    public Page<ReservationResponse> getReservations(
            Long clientId,
            Long roomId,
            Pageable pageable
    ) {

        Page<Reservation> reservations;

        // Both filters
        if (clientId != null && roomId != null) {
            reservations = reservationRepository
                    .findByUserIdAndRoomId(clientId, roomId, pageable);
        }

        // Filter only clientId Param
        else if (clientId != null) {
            reservations = reservationRepository
                    .findByUserId(clientId, pageable);
        }

        // Filter only roomId Param
        else if (roomId != null) {
            reservations = reservationRepository
                    .findByUserId(roomId, pageable);
        }

        // No Filters
        else {
            reservations = reservationRepository.findAll(pageable);
        }

        return reservations.map(this::toResponse);
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
        reservation.setAdults(request.getAdults());
        reservation.setChildren(request.getChildren());

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

        if (request.getAdults() != null) {
            reservation.setAdults(request.getAdults());

        }
        if (request.getChildren() != null) {
            reservation.setChildren(request.getChildren());
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
        response.setClientId(reservation.getUser().getId());
        response.setRoomId(reservation.getRoom().getId());
        response.setAdults(reservation.getAdults());
        response.setChildren(reservation.getChildren());
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
