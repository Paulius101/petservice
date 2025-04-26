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

    public List<PetServiceDTO> getAllServices() {
        return petServiceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PetServiceDTO getServiceById(Long id) {
        PetService service = petServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + id));
        return convertToDTO(service);
    }

    public PetServiceDTO createPetService(PetServiceDTO serviceDTO) {
        PetService service = convertToEntity(serviceDTO);
        PetService savedService = petServiceRepository.save(service);
        return convertToDTO(savedService);
    }

    public PetServiceDTO updateService(Long id, PetServiceDTO serviceDTO) {
        PetService existingService = petServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + id));

        BeanUtils.copyProperties(serviceDTO, existingService, "serviceId");
        PetService updatedService = petServiceRepository.save(existingService);
        return convertToDTO(updatedService);
    }

    public void deleteService(Long id) {
        petServiceRepository.deleteById(id);
    }

    private PetServiceDTO convertToDTO(PetService service) {
        PetServiceDTO dto = new PetServiceDTO();
        BeanUtils.copyProperties(service, dto);
        return dto;
    }

    private PetService convertToEntity(PetServiceDTO dto) {
        PetService service = new PetService();
        BeanUtils.copyProperties(dto, service);
        return service;
    }
}
