package com.example.petservice.service;
import com.example.petservice.converter.UserConverter;
import com.example.petservice.dto.UserDTO;
import com.example.petservice.entity.User;
import com.example.petservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userConverter = mock(UserConverter.class);
        userService = new UserService(userRepository, userConverter);
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userConverter.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userConverter).toDTO(user);
    }

    @Test
    void testGetUserById_whenFound() {
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userConverter.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserById_whenNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(999L));
    }

    @Test
    void testCreateUser_whenUnique() {
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");

        User user = new User();

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userConverter.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userConverter.toDTO(user)).thenReturn(dto);

        UserDTO result = userService.createUser(dto);

        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testCreateUser_whenDuplicateUsernameOrEmail() {
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.createUser(dto));
    }

    @Test
    void testUpdateUser_whenFound() {
        User existing = new User();
        UserDTO dto = new UserDTO();
        dto.setUsername("updated");
        dto.setOwnerName("New Owner");
        dto.setPhoneNumber("123456");
        dto.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);
        when(userConverter.toDTO(existing)).thenReturn(dto);

        UserDTO result = userService.updateUser(1L, dto);

        assertEquals("updated", existing.getUsername());
        assertEquals("New Owner", existing.getOwnerName());
        assertEquals("123456", existing.getPhoneNumber());
        assertEquals("updated@example.com", existing.getEmail());
    }

    @Test
    void testUpdateUser_whenNotFound() {
        UserDTO dto = new UserDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    void testDeleteUser_whenExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_whenNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        boolean result = userService.deleteUser(1L);

        assertFalse(result);
        verify(userRepository, never()).deleteById(any());
    }
}
