package com.example.petservice.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String ownerName;
    private String phoneNumber;
    private String email;
}
