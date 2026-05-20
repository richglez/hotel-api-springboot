package com.richglez.hotel.model;

import com.richglez.hotel.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    @Enumerated(EnumType.STRING) // guarda un string enves de indices 0,1,2
    @Column(length = 50) // Reducir el tamaño
    private RoomType roomType;

    private Double price;
    private Boolean available;
    capacity
    size
    name
    description

    @CreatedDate // Fecha de creado al hacer .save()
    @Column(updatable = false) // pero la DB nunca lo sobreescribe en un UPDATE .save()
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // null = activa / tiene fecha = eliminada
    private LocalDateTime deletedAt;
}
