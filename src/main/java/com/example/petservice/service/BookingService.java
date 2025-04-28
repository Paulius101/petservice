package com.example.petservice.service;

import com.example.petservice.dto.BookingDTO;
import com.example.petservice.entity.Booking;
import com.example.petservice.entity.PetService;
import com.example.petservice.entity.User;
import com.example.petservice.repository.BookingRepository;
import com.example.petservice.repository.PetServiceRepository;
import com.example.petservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PetServiceRepository petServiceRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, PetServiceRepository petServiceRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.petServiceRepository = petServiceRepository;
        this.userRepository = userRepository;
    }

    public List<BookingDTO> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id " + id));
        return convertToDTO(booking);
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id " + id));

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
                    .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + bookingDTO.getServiceId()));
            existingBooking.setService(service);
        }

        if (bookingDTO.getUserId() != null) {
            User user = userRepository.findById(bookingDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + bookingDTO.getUserId()));
            existingBooking.setUser(user);
        }

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return convertToDTO(updatedBooking);
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

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        BeanUtils.copyProperties(booking, dto);
        if (booking.getService() != null) {
            dto.setServiceId(booking.getService().getServiceId());
        }
        if (booking.getUser() != null) {
            dto.setUserId(booking.getUser().getUserId());
        }
        return dto;
    }

    private Booking convertToEntity(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setPetName(dto.getPetName());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setNotes(dto.getNotes());

        PetService service = petServiceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + dto.getServiceId()));
        booking.setService(service);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId()));
        booking.setUser(user);

        return booking;
    }
}
