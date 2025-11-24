package com.example.crud_sample.service;

import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("tony.stark@avengers.com")
                .username("ironman")
                .build();
    }

    @Test
    @DisplayName("Should return user when ID exists")
    void givenExistingId_whenGetById_thenReturnUser() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.getById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
    }

    @Test
    @DisplayName("Should return empty when ID does not exist")
    void givenNonExistingId_whenGetById_thenReturnEmptyOptional() {
        // Given
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<User> result = userService.getById(userId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return UserDetails when username exists")
    void givenExistingUsername_whenLoadUserByUsername_thenReturnUserDetails() {
        // Given
        String username = "ironman";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    @DisplayName("Should throw exception when username does not exist")
    void givenNonExistingUsername_whenLoadUserByUsername_thenThrowException() {
        // Given
        String username = "hulk";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> userService.loadUserByUsername(username));
    }

    @Test
    @DisplayName("Should save and return created user")
    void givenValidUser_whenCreate_thenReturnSavedUser() {
        // Given
        when(userRepository.save(user)).thenReturn(user);

        // When
        User savedUser = userService.create(user);

        // Then
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        assertEquals("ironman", savedUser.getUsername());
        verify(userRepository).save(user);
    }
}
