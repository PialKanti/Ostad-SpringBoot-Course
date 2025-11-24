package com.example.crud_sample;

import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.repository.UserRepository;
import com.example.crud_sample.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save user into database and return it")
    void givenValidUser_whenCreate_thenPersistInDatabase() {
        // Given
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .password("123")
                .email("steve.rogers@avengers.com")
                .username("captain")
                .build();

        // When
        User savedUser = userService.create(user);

        // Then
        assertNotNull(savedUser.getId());                  // ID generated
        assertEquals("captain", savedUser.getUsername());  // data correct

        // And verify DB actually saved it
        Optional<User> dbUser = userRepository.findById(savedUser.getId());

        assertTrue(dbUser.isPresent());
        assertEquals("steve.rogers@avengers.com", dbUser.get().getEmail());
    }
}
