package com.richglez.hotel.auth;

import com.richglez.hotel.enums.Roles;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Roles role;
}
