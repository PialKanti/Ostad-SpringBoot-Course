package com.example.crud_sample.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {
    METHOD_ARGUMENT_NOT_VALID("One or more fields do not match the validation parameters");

    private final String message;
}
