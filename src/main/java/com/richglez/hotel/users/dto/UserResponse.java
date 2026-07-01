package com.richglez.hotel.users.dto;

import java.time.LocalDateTime;
import lombok.Data;

/** User data returned by API endpoints. */
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
