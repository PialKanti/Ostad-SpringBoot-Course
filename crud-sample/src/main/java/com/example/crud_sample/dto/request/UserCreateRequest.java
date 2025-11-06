package com.example.crud_sample.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotBlank(message = "First Name can not be empty")
        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        @NotNull(message = "Email can not be empty")
        String email,

        @NotNull(message = "Username can not be empty")
        String username,

        ProfileCreateRequest profile
) {}
