package com.richglez.hotel.service;

import com.richglez.hotel.rooms.model.Room;
import com.richglez.hotel.rooms.repository.RoomRepository;
import com.richglez.hotel.rooms.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {


    @Mock // This creates a "fake" or "mock" version of
    private RoomRepository roomRepository;

    @InjectMocks // automatically injects the mocked RoomRepository into it
    private RoomService roomService;

    // Denotes that a method is a test case
    @Test
    void shouldReturnRoomById() {

        // Arrange
        Room room = new Room();
        room.setId(1L);
        room.setName("Luxury Sutie");

        // mock a fake response
        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));

        // Act
        var response = roomService.getRoomById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("Luxury Suite", response.getName());
        // verify if the results are the same

        verify(roomRepository).findById(1L);
    }
}
