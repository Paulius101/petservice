package com.example.petservice.dto;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Data
public class UserDTO {

    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
