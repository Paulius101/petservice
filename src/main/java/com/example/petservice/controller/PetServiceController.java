package com.example.petservice.controller;

import com.example.petservice.dto.PetServiceDTO;
import com.example.petservice.service.PetServiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class PetServiceController {

    private final PetServiceService petServiceService;

    public PetServiceController(PetServiceService petServiceService) {
        this.petServiceService = petServiceService;
    }

    @GetMapping
    public List<PetServiceDTO> getAllServices() {
        return petServiceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetServiceDTO> getServiceById(@PathVariable Long id) {
        PetServiceDTO serviceDTO = petServiceService.getServiceById(id);
        return ResponseEntity.ok(serviceDTO);
    }

    @PostMapping
    public ResponseEntity<PetServiceDTO> createPetService(@Valid @RequestBody PetServiceDTO serviceDTO) {
        PetServiceDTO createdService = petServiceService.createPetService(serviceDTO);
        return ResponseEntity.ok(createdService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetServiceDTO> updateService(@PathVariable Long id, @Valid @RequestBody PetServiceDTO serviceDTO) {
        PetServiceDTO updatedService = petServiceService.updateService(id, serviceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        boolean deleted = petServiceService.deleteService(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
