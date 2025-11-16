package com.example.crud_sample.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {
    READ("Read"),
    WRITE("Write");

    private final String displayValue;
}
