package com.richglez.hotel.dto;

import lombok.Data;

@Data
public class ClientPatchRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
}
