package com.example.crud_sample.dto.response;

import lombok.Builder;

@Builder
public record RegisteredUserResponse(String username,
                                     String email) {
}
