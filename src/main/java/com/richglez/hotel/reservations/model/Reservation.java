package com.richglez.hotel.reservations.model;

import com.richglez.hotel.rooms.model.Room;
import com.richglez.hotel.users.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter @Setter // enves de @Data, solo lo necesario
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // poblar automáticamente @CreatedDate y @LastModifiedDate
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @CreatedDate
    @Column(updatable = false) // pero la DB nunca lo sobreescribe en un UPDATE .save()
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    private LocalDateTime deletedAt;

}
