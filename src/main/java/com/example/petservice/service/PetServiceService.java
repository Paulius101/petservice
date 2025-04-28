package com.example.petservice.service;

import com.example.petservice.dto.PetServiceDTO;
import com.example.petservice.entity.PetService;
import com.example.petservice.repository.PetServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceService {
    private final PetServiceRepository petServiceRepository;

    public PetServiceService(PetServiceRepository petServiceRepository) {
        this.petServiceRepository = petServiceRepository;
    }

    // Get all services, converting each to DTO
    public List<PetServiceDTO> getAllServices() {
        return petServiceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a specific service by ID, returning the DTO
    public PetServiceDTO getServiceById(Long id) {
        PetService service = petServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + id));
        return convertToDTO(service);
    }

    // Create a new service and return the DTO
    public PetServiceDTO createPetService(PetServiceDTO serviceDTO) {
        PetService service = convertToEntity(serviceDTO);
        PetService savedService = petServiceRepository.save(service);
        return convertToDTO(savedService);
    }

    // Update an existing service and return the updated DTO
    public PetServiceDTO updateService(Long id, PetServiceDTO serviceDTO) {
        PetService existingService = petServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + id));

        BeanUtils.copyProperties(serviceDTO, existingService, "serviceId");
        PetService updatedService = petServiceRepository.save(existingService);
        return convertToDTO(updatedService);
    }

    // Delete a service by ID
    public void deleteService(Long id) {
        petServiceRepository.deleteById(id);
    }

    // Convert PetService entity to DTO
    private PetServiceDTO convertToDTO(PetService service) {
        PetServiceDTO dto = new PetServiceDTO();
        BeanUtils.copyProperties(service, dto);

        // Split the description into an array of strings, splitting by newlines or semicolons
        if (service.getDescription() != null && !service.getDescription().isEmpty()) {
            // Split the description by newline character (or you can use semicolon or other delimiters)
            String[] descriptionArray = service.getDescription().split("\\n");
            dto.setDescription(List.of(descriptionArray)); // Convert array to list
        }

        return dto;
    }

    // Convert PetServiceDTO to entity
    private PetService convertToEntity(PetServiceDTO dto) {
        PetService service = new PetService();
        BeanUtils.copyProperties(dto, service);
        return service;
    }
}
