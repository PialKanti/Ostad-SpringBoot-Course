package com.example.crud_sample.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN("Admin"),
    MODERATOR("Moderator"),
    USER("User");

    private final String displayValue;
}
