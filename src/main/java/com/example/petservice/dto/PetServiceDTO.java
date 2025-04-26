package com.example.petservice.dto;

import lombok.Data;

@Data
public class PetServiceDTO {
    private Long serviceId;
    private String name;
    private String description;
    private Double price;
}
