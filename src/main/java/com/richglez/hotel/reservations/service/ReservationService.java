package com.richglez.hotel.reservations.service;

import com.richglez.hotel.reservations.dto.ReservationRequest;
import com.richglez.hotel.reservations.dto.ReservationResponse;
import com.richglez.hotel.reservations.model.Reservation;
import com.richglez.hotel.reservations.repository.ReservationRepository;
import com.richglez.hotel.rooms.model.Room;
import com.richglez.hotel.rooms.repository.RoomRepository;
import com.richglez.hotel.users.model.User;
import com.richglez.hotel.users.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Provides reservation management operations. */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    /** Creates a reservation service. */
    public ReservationService(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    /** Creates a reservation. */
    public ReservationResponse saveReservation(ReservationRequest request) {
        User user = findUser(request.getClientId());
        Room room = findRoom(request.getRoomId());
        validateDateRange(request);

        Reservation reservation = new Reservation();
        applyReservationRequest(reservation, request, user, room);

        return toResponse(reservationRepository.save(reservation));
    }

    /** Returns reservations using optional filters. */
    public Page<ReservationResponse> getReservations(
            Long clientId,
            Long roomId,
            Pageable pageable) {
        Page<Reservation> reservations;

        if (clientId != null && roomId != null) {
            reservations = reservationRepository.findByUserIdAndRoomId(clientId, roomId, pageable);
        } else if (clientId != null) {
            reservations = reservationRepository.findByUserId(clientId, pageable);
        } else if (roomId != null) {
            reservations = reservationRepository.findByRoomId(roomId, pageable);
        } else {
            reservations = reservationRepository.findAll(pageable);
        }

        return reservations.map(this::toResponse);
    }

    /** Returns a reservation by id. */
    public ReservationResponse getReservationById(Long id) {
        return toResponse(findReservationById(id));
    }

    /** Replaces a reservation by id. */
    public ReservationResponse updateReservation(Long id, ReservationRequest request) {
        Reservation reservation = findReservationById(id);
        User user = findUser(request.getClientId());
        Room room = findRoom(request.getRoomId());

        validateDateRange(request);
        applyReservationRequest(reservation, request, user, room);

        return toResponse(reservationRepository.save(reservation));
    }

    /** Partially updates a reservation by id. */
    public ReservationResponse patchReservation(Long id, ReservationRequest request) {
        Reservation reservation = findReservationById(id);
        validatePatchDateRange(request);

        if (request.getCheckIn() != null) {
            reservation.setCheckIn(request.getCheckIn());
        }
        if (request.getCheckOut() != null) {
            reservation.setCheckOut(request.getCheckOut());
        }
        if (request.getClientId() != null) {
            reservation.setUser(findUser(request.getClientId()));
        }
        if (request.getRoomId() != null) {
            reservation.setRoom(findRoom(request.getRoomId()));
        }
        if (request.getAdults() != null) {
            reservation.setAdults(request.getAdults());
        }
        if (request.getChildren() != null) {
            reservation.setChildren(request.getChildren());
        }

        return toResponse(reservationRepository.save(reservation));
    }

    /** Soft deletes a reservation by id. */
    public ReservationResponse softDeleteReservation(Long id) {
        Reservation reservation = findReservationById(id);

        reservation.setDeletedAt(LocalDateTime.now());
        return toResponse(reservationRepository.save(reservation));
    }

    /** Permanently deletes a reservation by id. */
    public void hardDeleteReservation(long id) {
        Reservation reservation = findReservationById(id);

        reservationRepository.delete(reservation);
    }

    /** Maps a reservation entity to its API response. */
    public ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCheckIn(reservation.getCheckIn());
        response.setCheckOut(reservation.getCheckOut());
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

    private Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reservation not found"));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Client not found"));
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Room not found"));
    }

    private void validateDateRange(ReservationRequest request) {
        if (request.getCheckOut().isBefore(request.getCheckIn())) {
            throw invalidDateRangeException();
        }
    }

    private void validatePatchDateRange(ReservationRequest request) {
        if (request.getCheckIn() != null
                && request.getCheckOut() != null
                && request.getCheckOut().isBefore(request.getCheckIn())) {
            throw invalidDateRangeException();
        }
    }

    private ResponseStatusException invalidDateRangeException() {
        return new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Check-in must be before check-out");
    }

    private void applyReservationRequest(
            Reservation reservation,
            ReservationRequest request,
            User user,
            Room room) {
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setAdults(request.getAdults());
        reservation.setChildren(request.getChildren());
    }
}
