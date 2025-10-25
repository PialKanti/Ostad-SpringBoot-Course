package com.example.crud_sample.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
        @NotBlank(message = "Title is required")
        String title,

        String content
) {}
