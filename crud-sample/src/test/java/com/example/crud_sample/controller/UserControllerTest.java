package com.example.crud_sample.controller;

import com.example.crud_sample.mapper.PostMapper;
import com.example.crud_sample.mapper.UserMapper;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.service.JwtService;
import com.example.crud_sample.service.PostService;
import com.example.crud_sample.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserService.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private PostMapper postMapper;

    @MockitoBean
    private JwtService jwtService;

    @WithMockUser
    @Test
    @DisplayName("GET /users/{id} should return 200 OK and user details")
    void should_Return200_When_RequestIsValid() throws Exception {
        // Given
        User user = User.builder()
                .id(1L)
                .email("tony.stark@avengers.com")
                .username("ironman")
                .build();

        when(userService.getById(1L)).thenReturn(Optional.of(user));

        // When & Then
        mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("ironman"))
                .andExpect(jsonPath("$.email").value("tony.stark@avengers.com"));
    }
}
