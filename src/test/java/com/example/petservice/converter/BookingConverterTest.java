package com.example.petservice.converter;

import com.example.petservice.dto.BookingDTO;
import com.example.petservice.entity.Booking;
import com.example.petservice.entity.PetService;
import com.example.petservice.entity.User;
import com.example.petservice.repository.PetServiceRepository;
import com.example.petservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingConverterTest {

    private BookingConverter bookingConverter;
    private PetServiceRepository petServiceRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        petServiceRepository = mock(PetServiceRepository.class);
        userRepository = mock(UserRepository.class);

        bookingConverter = new BookingConverter(petServiceRepository, userRepository);
        bookingConverter.petServiceRepository = petServiceRepository;
        bookingConverter.userRepository = userRepository;
    }

    @Test
    void testToEntity_SuccessfulConversion() {
        BookingDTO dto = new BookingDTO();
        dto.setPetName("Ogis");
        dto.setStartDate(LocalDate.of(2023, 1, 1));
        dto.setEndDate(LocalDate.of(2023, 1, 5));
        dto.setNotes("Draugiskas");
        dto.setServiceId(1L);
        dto.setUserId(2L);

        PetService service = new PetService();
        service.setServiceId(1L);
        service.setName("SPA");

        User user = new User();
        user.setUserId(2L);
        user.setUsername("TestUser");

        when(petServiceRepository.findById(1L)).thenReturn(java.util.Optional.of(service));
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(user));

        Booking entity = bookingConverter.toEntity(dto);

        assertEquals("Ogis", entity.getPetName());
        assertEquals(LocalDate.of(2023, 1, 1), entity.getStartDate());
        assertEquals(LocalDate.of(2023, 1, 5), entity.getEndDate());
        assertEquals("Draugiskas", entity.getNotes());
        assertEquals(service, entity.getService());
        assertEquals(user, entity.getUser());
    }

    @Test
    void testToEntity_ServiceNotFound_ThrowsException() {
        BookingDTO dto = new BookingDTO();
        dto.setServiceId(99L);
        dto.setUserId(2L);

        when(petServiceRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookingConverter.toEntity(dto);
        });

        assertTrue(exception.getMessage().contains("Service not found"));
    }

    @Test
    void testToEntity_UserNotFound_ThrowsException() {
        BookingDTO dto = new BookingDTO();
        dto.setServiceId(1L);
        dto.setUserId(88L);

        PetService mockService = new PetService();
        when(petServiceRepository.findById(1L)).thenReturn(java.util.Optional.of(mockService));
        when(userRepository.findById(88L)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookingConverter.toEntity(dto);
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testToDTO_MapsFieldsCorrectly() {
        Booking entity = new Booking();
        entity.setBookingId(5L);
        entity.setPetName("Prince");
        entity.setStartDate(LocalDate.of(2023, 6, 1));
        entity.setEndDate(LocalDate.of(2023, 6, 3));
        entity.setNotes("Kartais pikta");

        PetService service = new PetService();
        service.setServiceId(10L);
        service.setName("SPA");

        User user = new User();
        user.setUserId(7L);

        entity.setService(service);
        entity.setUser(user);

        BookingDTO dto = bookingConverter.toDTO(entity);

        assertEquals(5L, dto.getBookingId());
        assertEquals("Prince", dto.getPetName());
        assertEquals(LocalDate.of(2023, 6, 1), dto.getStartDate());
        assertEquals(LocalDate.of(2023, 6, 3), dto.getEndDate());
        assertEquals("Kartais pikta", dto.getNotes());
        assertEquals(10L, dto.getServiceId());
        assertEquals("SPA", dto.getServiceName());
        assertEquals(7L, dto.getUserId());
    }
}
