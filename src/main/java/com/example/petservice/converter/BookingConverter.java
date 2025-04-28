package com.example.petservice.converter;

import jakarta.persistence.EntityNotFoundException;
import com.example.petservice.dto.BookingDTO;
import com.example.petservice.entity.Booking;
import com.example.petservice.entity.PetService;
import com.example.petservice.entity.User;
import com.example.petservice.repository.PetServiceRepository;
import com.example.petservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {

    @Autowired
    private PetServiceRepository petServiceRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setPetName(bookingDTO.getPetName());
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setNotes(bookingDTO.getNotes());

        PetService service = petServiceRepository.findById(bookingDTO.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id " + bookingDTO.getServiceId()));
        booking.setService(service);

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + bookingDTO.getUserId()));
        booking.setUser(user);

        return booking;
    }

    public BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setPetName(booking.getPetName());
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setNotes(booking.getNotes());

        if (booking.getService() != null) {
            bookingDTO.setServiceId(booking.getService().getServiceId());
            bookingDTO.setServiceName(booking.getService().getName());
        }

        if (booking.getUser() != null) {
            bookingDTO.setUserId(booking.getUser().getUserId());
        }

        return bookingDTO;
    }
}
