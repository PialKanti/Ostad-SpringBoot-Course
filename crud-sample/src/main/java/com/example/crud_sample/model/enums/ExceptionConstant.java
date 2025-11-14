package com.example.crud_sample.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {
    USER_NOT_FOUND("User not found. Please check your credentials."),
    METHOD_ARGUMENT_NOT_VALID("One or more fields do not match the validation parameters."),
    INVALID_JWT_TOKEN("JWT missing or invalid."),
    JWT_TOKEN_EXPIRED("JWT token has expired. Please log in again.");

    private final String message;
}
