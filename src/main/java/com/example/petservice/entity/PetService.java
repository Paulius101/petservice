package com.example.petservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "services")
@Getter
@Setter
public class PetService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String name;
    @Column(length = 1024)
    private String description;
    private Double price;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
