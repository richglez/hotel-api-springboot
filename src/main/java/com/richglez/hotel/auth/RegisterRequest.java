package com.richglez.hotel.auth;

import com.richglez.hotel.enums.Roles;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    private String phone;
    // role no se expone — el backend asigna GUEST por defecto
}
