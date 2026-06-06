package com.richglez.hotel.reservations.service;

import com.richglez.hotel.reservations.dto.ReservationRequest;
import com.richglez.hotel.reservations.repository.ReservationRepository;
import com.richglez.hotel.rooms.repository.RoomRepository;
import com.richglez.hotel.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Unit Test
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldThrowExceptionWhenCheckoutBeforeCheckin() {

        // 1. Arrange -> setting the fake data
        ReservationRequest request = new ReservationRequest();

        // checkIn = tomorrow
        request.setCheckIn(LocalDateTime.now().plusDays(1)); // simular el error

        // checkOut = today
        request.setCheckOut(LocalDateTime.now());


        // 2. Act + Assert -> ejecuto método y espero excepción
        assertThrows(
                ResponseStatusException.class,
                () -> reservationService.saveReservation(request) // aqui ocurre el evento
        );
        // que pasa si mando estos datos erroneos en mi metodo original?

        // Verify -> confirmo que save nunca ocurrió
        verify(reservationRepository, never()).save(any());

    }
}

// UNIT TEST