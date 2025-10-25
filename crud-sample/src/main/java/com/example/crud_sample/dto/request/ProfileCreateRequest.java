package com.example.crud_sample.dto.request;

import com.example.crud_sample.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProfileCreateRequest(
        String bio,

        @JsonProperty("phone_number")
        String phoneNumber,

        Gender gender
) {}
