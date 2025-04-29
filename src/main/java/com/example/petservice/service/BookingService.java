package com.example.petservice.service;

import com.example.petservice.dto.BookingDTO;
import com.example.petservice.entity.Booking;
import com.example.petservice.entity.PetService;
import com.example.petservice.entity.User;
import com.example.petservice.exception.ResourceNotFoundException;
import com.example.petservice.repository.BookingRepository;
import com.example.petservice.repository.PetServiceRepository;
import com.example.petservice.repository.UserRepository;
import com.example.petservice.converter.BookingConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PetServiceRepository petServiceRepository;
    private final UserRepository userRepository;
    private final BookingConverter bookingConverter; // Inject converter

    public BookingService(BookingRepository bookingRepository, PetServiceRepository petServiceRepository, UserRepository userRepository, BookingConverter bookingConverter) {
        this.bookingRepository = bookingRepository;
        this.petServiceRepository = petServiceRepository;
        this.userRepository = userRepository;
        this.bookingConverter = bookingConverter;
    }

    public List<BookingDTO> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUser_UserId(userId).stream()
                .map(bookingConverter::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
        return bookingConverter.toDTO(booking);
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = bookingConverter.toEntity(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingConverter.toDTO(savedBooking);
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        if (bookingDTO.getPetName() != null) {
            existingBooking.setPetName(bookingDTO.getPetName());
        }

        if (bookingDTO.getStartDate() != null) {
            existingBooking.setStartDate(bookingDTO.getStartDate());
        }

        if (bookingDTO.getEndDate() != null) {
            existingBooking.setEndDate(bookingDTO.getEndDate());
        }

        if (bookingDTO.getNotes() != null) {
            existingBooking.setNotes(bookingDTO.getNotes());
        }

        if (bookingDTO.getServiceId() != null) {
            PetService service = petServiceRepository.findById(bookingDTO.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + bookingDTO.getServiceId()));
            existingBooking.setService(service);
        }

        if (bookingDTO.getUserId() != null) {
            User user = userRepository.findById(bookingDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + bookingDTO.getUserId()));
            existingBooking.setUser(user);
        }

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return bookingConverter.toDTO(updatedBooking); // Use converter
    }

    public boolean deleteBooking(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}