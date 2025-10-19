package com.example.crud_sample.controller;

import com.example.crud_sample.dto.request.UserCreateRequest;
import com.example.crud_sample.mapper.UserMapper;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public User createUser(@Valid @RequestBody UserCreateRequest request) {
        User user = userMapper.toEntity(request);
        return userService.create(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }
}
