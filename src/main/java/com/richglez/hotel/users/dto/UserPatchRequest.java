package com.richglez.hotel.users.dto;

import lombok.Data;

/** Optional fields for partially updating a user. */
@Data
public class UserPatchRequest {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
