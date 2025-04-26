package com.example.petservice.repository;

import com.example.petservice.entity.PetService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetServiceRepository extends JpaRepository<PetService, Long> {
}
