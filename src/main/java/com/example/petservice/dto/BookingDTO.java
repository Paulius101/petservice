package com.example.petservice.dto;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class BookingDTO {

    private Long bookingId;

    @NotBlank(message = "Pet name cannot be empty")
    @Size(min = 2, max = 100, message = "Pet name must be between 2 and 100 characters")
    private String petName;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @Size(max = 500, message = "Notes cannot exceed 1024 characters")
    private String notes;

    @NotNull(message = "Service ID cannot be null")
    private Long serviceId;

    private String serviceName;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
