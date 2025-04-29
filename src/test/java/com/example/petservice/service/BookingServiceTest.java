package com.example.petservice.service;
import com.example.petservice.converter.BookingConverter;
import com.example.petservice.dto.BookingDTO;
import com.example.petservice.entity.Booking;
import com.example.petservice.repository.BookingRepository;
import com.example.petservice.repository.PetServiceRepository;
import com.example.petservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private PetServiceRepository petServiceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingConverter bookingConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookingsByUserId_returnsBookingList() {
        Long userId = 1L;
        Booking booking = new Booking();
        BookingDTO bookingDTO = new BookingDTO();

        when(bookingRepository.findByUser_UserId(userId)).thenReturn(List.of(booking));
        when(bookingConverter.toDTO(booking)).thenReturn(bookingDTO);

        List<BookingDTO> result = bookingService.getBookingsByUserId(userId);

        assertEquals(1, result.size());
        verify(bookingRepository).findByUser_UserId(userId);
        verify(bookingConverter).toDTO(booking);
    }

    @Test
    void testGetBookingById_found() {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(1L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingConverter.toDTO(booking)).thenReturn(dto);

        BookingDTO result = bookingService.getBookingById(1L);

        assertEquals(1L, result.getBookingId());
    }

    @Test
    void testGetBookingById_notFound_throws() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.getBookingById(1L));
    }

    @Test
    void testCreateBooking_success() {
        BookingDTO dto = new BookingDTO();
        Booking entity = new Booking();
        Booking saved = new Booking();
        BookingDTO resultDTO = new BookingDTO();

        when(bookingConverter.toEntity(dto)).thenReturn(entity);
        when(bookingRepository.save(entity)).thenReturn(saved);
        when(bookingConverter.toDTO(saved)).thenReturn(resultDTO);

        BookingDTO result = bookingService.createBooking(dto);

        assertNotNull(result);
    }

    @Test
    void testUpdateBooking_success() {
        Long id = 1L;
        Booking existing = new Booking();
        existing.setBookingId(id);

        BookingDTO dto = new BookingDTO();
        dto.setPetName("UpdatedName");

        Booking updated = new Booking();
        updated.setPetName("UpdatedName");

        when(bookingRepository.findById(id)).thenReturn(Optional.of(existing));
        when(bookingRepository.save(existing)).thenReturn(updated);
        when(bookingConverter.toDTO(updated)).thenReturn(new BookingDTO());

        BookingDTO result = bookingService.updateBooking(id, dto);

        assertNotNull(result);
        assertEquals("UpdatedName", existing.getPetName());
    }

    @Test
    void testDeleteBooking_found_returnsTrue() {
        Booking booking = new Booking();
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        boolean deleted = bookingService.deleteBooking(1L);

        assertTrue(deleted);
        verify(bookingRepository).deleteById(1L);
    }

    @Test
    void testDeleteBooking_notFound_returnsFalse() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        boolean deleted = bookingService.deleteBooking(1L);

        assertFalse(deleted);
    }
}
