package com.example.petservice.service;

import com.example.petservice.converter.UserConverter;
import com.example.petservice.dto.UserDTO;
import com.example.petservice.entity.User;
import com.example.petservice.exception.ResourceNotFoundException;
import com.example.petservice.exception.ConflictException;
import com.example.petservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return userConverter.toDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername()) || userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ConflictException("User already exists with the same username or email");
        }

        User user = userConverter.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userConverter.toDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setOwnerName(userDTO.getOwnerName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setEmail(userDTO.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return userConverter.toDTO(updatedUser);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
