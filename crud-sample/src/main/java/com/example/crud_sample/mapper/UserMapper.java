package com.example.crud_sample.mapper;

import com.example.crud_sample.dto.request.UserCreateRequest;
import com.example.crud_sample.dto.request.UserUpdateRequest;
import com.example.crud_sample.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreateRequest request) {
        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setUsername(request.username());

        return user;
    }

    public User toEntity(User existingUser, UserUpdateRequest request) {
        existingUser.setFirstName(request.firstName());
        existingUser.setLastName(request.lastName());

        return existingUser;
    }
}
