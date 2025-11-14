package com.example.crud_sample.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record ApiResponse<T>(String message,
                             @JsonInclude(JsonInclude.Include.NON_NULL)
                             T data) {
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}
