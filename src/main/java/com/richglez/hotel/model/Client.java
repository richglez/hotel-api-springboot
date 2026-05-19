package com.richglez.hotel.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data // (POJOs) generando automáticamente getters, setters, etc
@Entity
@EntityListeners(AuditingEntityListener.class)
// activa la auditoria Activa el listener que detecta los eventos de persistencia
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;

    @CreatedDate // Fecha de creado al hacer .save()
    @Column(updatable = false) // pero la DB nunca lo sobreescribe en un UPDATE .save()
    private LocalDateTime createdAt;

    @LastModifiedDate // Spring actualiza la fecha al hacer .save()
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt; // null = activo / con fecha = deleted

}
