package com.example.crud_sample.dto.request;

import com.example.crud_sample.model.enums.Gender;

public record UserSearchRequest(String firstName,
                                String lastName,
                                String username,
                                Gender gender,
                                String phoneNumber) {
}
