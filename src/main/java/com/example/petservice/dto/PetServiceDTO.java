package com.example.petservice.dto;
import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Data
public class PetServiceDTO {

    private Long serviceId;

    @NotBlank(message = "Service name cannot be empty")
    @Size(min = 3, max = 100, message = "Service name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 1024, message = "Description should be between 1 and 1024 characters")
    private List<String> description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    private Double price;
}
