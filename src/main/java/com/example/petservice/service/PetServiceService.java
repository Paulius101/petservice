package com.example.petservice.service;

import com.example.petservice.converter.PetServiceConverter;
import com.example.petservice.dto.PetServiceDTO;
import com.example.petservice.entity.PetService;
import com.example.petservice.exception.ResourceNotFoundException;
import com.example.petservice.repository.PetServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceService {
    private final PetServiceRepository petServiceRepository;
    private final PetServiceConverter petServiceConverter;

    public PetServiceService(PetServiceRepository petServiceRepository, PetServiceConverter petServiceConverter) {
        this.petServiceRepository = petServiceRepository;
        this.petServiceConverter = petServiceConverter;
    }

    public List<PetServiceDTO> getAllServices() {
        return petServiceRepository.findAll().stream()
                .map(petServiceConverter::toDTO)
                .collect(Collectors.toList());
    }

    public PetServiceDTO getServiceById(Long id) {
        PetService service = petServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + id));  // Throwing ResourceNotFoundException
        return petServiceConverter.toDTO(service);
    }

    public PetServiceDTO createPetService(PetServiceDTO serviceDTO) {
        PetService service = petServiceConverter.toEntity(serviceDTO);
        PetService savedService = petServiceRepository.save(service);
        return petServiceConverter.toDTO(savedService);
    }

    public PetServiceDTO updateService(Long id, PetServiceDTO serviceDTO) {
        PetService existingService = petServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + id));

        existingService.setName(serviceDTO.getName());
        existingService.setPrice(serviceDTO.getPrice());
        existingService.setDescription(String.join("\n", serviceDTO.getDescription()));

        PetService updatedService = petServiceRepository.save(existingService);
        return petServiceConverter.toDTO(updatedService);
    }


    public boolean deleteService(Long id) {
        if (petServiceRepository.existsById(id)) {
            petServiceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
