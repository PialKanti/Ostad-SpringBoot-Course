package com.example.crud_sample.dto.response;

import lombok.Builder;

@Builder
public record ValidationErrorResponse(String field, String message) {
}
