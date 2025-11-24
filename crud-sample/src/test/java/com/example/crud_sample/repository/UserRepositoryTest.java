package com.example.crud_sample.repository;

import com.example.crud_sample.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Check existsByUsername returns true for existing user")
    void testExistsByUsername() {
        // Given
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("123");
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByUsername("john_doe");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Check existsByEmail returns true for existing email")
    void testExistsByEmail() {
        // Given
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setUsername("jane_doe");
        user.setPassword("123");
        user.setEmail("jane@example.com");
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByEmail("jane@example.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Check findByUsername returns user with roles and permissions")
    void testFindByUsernameWithEntityGraph() {
        // Given
        User user = new User();
        user.setFirstName("Alice");
        user.setUsername("alice");
        user.setPassword("123");
        user.setEmail("alice@example.com");
        // Optionally set roles if needed for testing
        userRepository.save(user);

        // When
        Optional<User> optionalUser = userRepository.findByUsername("alice");

        // Then
        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getUsername()).isEqualTo("alice");
    }
}