package com.richglez.hotel.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** Required fields for replacing a user. */
@Data
public class UserPutRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    private String phone;
}
