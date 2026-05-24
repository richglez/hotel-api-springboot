package com.richglez.hotel.users.dto;

import lombok.Data;

@Data
public class UserPatchRequest {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
