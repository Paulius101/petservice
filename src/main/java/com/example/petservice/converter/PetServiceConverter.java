package com.example.petservice.converter;

import com.example.petservice.dto.PetServiceDTO;
import com.example.petservice.entity.PetService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetServiceConverter {

    public PetService toEntity(PetServiceDTO dto) {
        PetService service = new PetService();
        service.setName(dto.getName());
        service.setPrice(dto.getPrice());

        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            service.setDescription(String.join("\n", dto.getDescription()));
        } else {
            service.setDescription(null);
        }

        return service;
    }

    public PetServiceDTO toDTO(PetService service) {
        PetServiceDTO dto = new PetServiceDTO();
        dto.setServiceId(service.getServiceId());
        dto.setName(service.getName());
        dto.setPrice(service.getPrice());

        if (service.getDescription() != null && !service.getDescription().isEmpty()) {
            String[] parts = service.getDescription().split("\\n");
            dto.setDescription(List.of(parts));
        }

        return dto;
    }
}
