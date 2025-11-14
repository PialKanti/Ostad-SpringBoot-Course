package com.example.crud_sample.mapper;

import com.example.crud_sample.dto.request.UserCreateRequest;
import com.example.crud_sample.dto.request.UserRegistrationRequest;
import com.example.crud_sample.dto.request.UserUpdateRequest;
import com.example.crud_sample.dto.response.RegisteredUserResponse;
import com.example.crud_sample.model.entity.Profile;
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

        if(request.profile() != null) {
            Profile profile = new Profile();
            profile.setBio(request.profile().bio());
            profile.setPhoneNumber(request.profile().phoneNumber());
            profile.setGender(request.profile().gender());
            profile.setUser(user);

            user.setProfile(profile);
        }

        return user;
    }

    public User toEntity(UserRegistrationRequest request) {
        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(request.password());

        if(request.profile() != null) {
            Profile profile = new Profile();
            profile.setBio(request.profile().bio());
            profile.setPhoneNumber(request.profile().phoneNumber());
            profile.setGender(request.profile().gender());
            profile.setUser(user);

            user.setProfile(profile);
        }

        return user;
    }

    public User toEntity(User existingUser, UserUpdateRequest request) {
        existingUser.setFirstName(request.firstName());
        existingUser.setLastName(request.lastName());

        return existingUser;
    }

    public RegisteredUserResponse toRegisteredUserResponse(User user) {
        return RegisteredUserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
