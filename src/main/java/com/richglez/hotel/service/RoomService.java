package com.richglez.hotel.service;

import com.richglez.hotel.model.Room;
import com.richglez.hotel.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + id + " not found"));
    }

    public Room newRoom(Room room){
        return roomRepository.save(room);
    }
}
