package com.example.petservice.dto;

import lombok.Data;

@Data
public class ServiceDTO {
    private Long serviceId;
    private String name;
    private String description;
    private Double price;
}
