package com.example.crud_sample.controller;

import com.example.crud_sample.dto.request.PostCreateRequest;
import com.example.crud_sample.dto.request.UserCreateRequest;
import com.example.crud_sample.dto.request.UserUpdateRequest;
import com.example.crud_sample.mapper.PostMapper;
import com.example.crud_sample.mapper.UserMapper;
import com.example.crud_sample.model.entity.Post;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.service.PostService;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

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

        if (userService.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body(Map.of("message", "email already exists"));
        }

        if (userService.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body(Map.of("message", "username already exists"));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long userId,
                                           @Valid @RequestBody PostCreateRequest request) {
        Post post = postMapper.toEntity(request);

        Post savedPost = postService.createPostForUser(userId, post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(savedPost);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{userId}/posts/{postId}")
    public ResponseEntity<Void> deletePostOfUser(@PathVariable Long userId,
                                                 @PathVariable Long postId) {
        boolean deleted = postService.deletePostForUser(userId, postId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
