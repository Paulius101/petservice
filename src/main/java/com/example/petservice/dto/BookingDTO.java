package com.example.petservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {
    private Long bookingId;
    private String petName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
    private Long serviceId;
    private String serviceName;
    private Long userId;
}
