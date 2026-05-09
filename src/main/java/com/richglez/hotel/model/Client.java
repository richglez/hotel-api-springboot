package com.richglez.hotel.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;

}
