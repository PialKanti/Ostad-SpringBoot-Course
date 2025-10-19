package com.example.crud_sample.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(@NotNull @JsonProperty("first_name") String firstName,
                                @JsonProperty("last_name") String lastName,
                                String email,
                                String username) {
}
