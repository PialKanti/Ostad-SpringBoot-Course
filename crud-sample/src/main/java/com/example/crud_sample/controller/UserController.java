package com.example.crud_sample.controller;

import com.example.crud_sample.dto.request.UserCreateRequest;
import com.example.crud_sample.dto.request.UserUpdateRequest;
import com.example.crud_sample.mapper.UserMapper;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest request,
                                             BindingResult bindingResult) {
        // Bad code. We will replace it with Global Exception Handler
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    bindingResult.getFieldErrors().stream().collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    )
            );
        }

        User user = userMapper.toEntity(request);
        User savedUser = userService.create(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody UserUpdateRequest updateRequest) {
        Optional<User> userOptional = userService.getById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = userOptional.get();
        User userToBeUpdated = userMapper.toEntity(existingUser, updateRequest);
        userService.save(userToBeUpdated);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getById(id);
        return userOptional
                .map(user ->
                        ResponseEntity
                                .ok()
                                .body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
