package com.example.petservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class PetServiceDTO {
    private Long serviceId;
    private String name;
    private List<String> description;
    private Double price;
}
