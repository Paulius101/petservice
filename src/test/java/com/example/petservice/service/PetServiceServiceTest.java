package com.example.petservice.service;

import com.example.petservice.converter.PetServiceConverter;
import com.example.petservice.dto.PetServiceDTO;
import com.example.petservice.entity.PetService;
import com.example.petservice.exception.ResourceNotFoundException;
import com.example.petservice.repository.PetServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceServiceTest {

    private PetServiceRepository petServiceRepository;
    private PetServiceConverter petServiceConverter;
    private PetServiceService petServiceService;

    @BeforeEach
    void setUp() {
        petServiceRepository = mock(PetServiceRepository.class);
        petServiceConverter = mock(PetServiceConverter.class);
        petServiceService = new PetServiceService(petServiceRepository, petServiceConverter);
    }

    @Test
    void testGetAllServices() {
        PetService service = new PetService();
        PetServiceDTO dto = new PetServiceDTO();

        when(petServiceRepository.findAll()).thenReturn(List.of(service));
        when(petServiceConverter.toDTO(service)).thenReturn(dto);

        List<PetServiceDTO> result = petServiceService.getAllServices();

        assertEquals(1, result.size());
        verify(petServiceRepository).findAll();
        verify(petServiceConverter).toDTO(service);
    }

    @Test
    void testGetServiceById_whenFound() {
        PetService service = new PetService();
        PetServiceDTO dto = new PetServiceDTO();

        when(petServiceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(petServiceConverter.toDTO(service)).thenReturn(dto);

        PetServiceDTO result = petServiceService.getServiceById(1L);

        assertNotNull(result);
        verify(petServiceRepository).findById(1L);
    }

    @Test
    void testGetServiceById_whenNotFound() {
        when(petServiceRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            petServiceService.getServiceById(999L);
        });
    }

    @Test
    void testCreatePetService() {
        PetServiceDTO dto = new PetServiceDTO();
        PetService entity = new PetService();

        when(petServiceConverter.toEntity(dto)).thenReturn(entity);
        when(petServiceRepository.save(entity)).thenReturn(entity);
        when(petServiceConverter.toDTO(entity)).thenReturn(dto);

        PetServiceDTO result = petServiceService.createPetService(dto);

        assertNotNull(result);
        verify(petServiceRepository).save(entity);
    }

    @Test
    void testUpdateService_whenFound() {
        PetService existing = new PetService();
        PetServiceDTO dto = new PetServiceDTO();
        dto.setName("Updated");
        dto.setPrice(99.99);
        dto.setDescription(List.of("desc1", "desc2"));

        when(petServiceRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(petServiceRepository.save(existing)).thenReturn(existing);
        when(petServiceConverter.toDTO(existing)).thenReturn(dto);

        PetServiceDTO result = petServiceService.updateService(1L, dto);

        assertEquals("Updated", existing.getName());
        assertEquals(99.99, existing.getPrice());
        assertEquals("desc1\ndesc2", existing.getDescription());
        verify(petServiceRepository).save(existing);
    }

    @Test
    void testUpdateService_whenNotFound() {
        when(petServiceRepository.findById(1L)).thenReturn(Optional.empty());
        PetServiceDTO dto = new PetServiceDTO();

        assertThrows(ResourceNotFoundException.class, () -> {
            petServiceService.updateService(1L, dto);
        });
    }

    @Test
    void testDeleteService() {
        when(petServiceRepository.existsById(1L)).thenReturn(true);

        petServiceService.deleteService(1L);

        verify(petServiceRepository).deleteById(1L);
    }

}
