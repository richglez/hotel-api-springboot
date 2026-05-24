package com.richglez.hotel.rooms.model;

import com.richglez.hotel.common.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter @Setter // enves de @Data, solo lo necesario
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
    private Integer capacity; // cuántas personas caben
    private Double size; // m²
    private String name; // titulo de la recamara
    private String description; // // descripción visible al cliente

    @CreatedDate // Fecha de creado al hacer .save()
    @Column(updatable = false) // pero la DB nunca lo sobreescribe en un UPDATE .save()
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // null = activa / tiene fecha = eliminada
    private LocalDateTime deletedAt;
}
