package com.example.crud_sample.controller;

import com.example.crud_sample.dto.request.LoginRequest;
import com.example.crud_sample.dto.request.UserRegistrationRequest;
import com.example.crud_sample.dto.response.ApiResponse;
import com.example.crud_sample.dto.response.LoginResponse;
import com.example.crud_sample.dto.response.RegisteredUserResponse;
import com.example.crud_sample.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.crud_sample.constant.ApplicationConstant.LOGIN_SUCCESSFUL;
import static com.example.crud_sample.constant.ApplicationConstant.USER_REGISTRATION_SUCCESSFUL;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisteredUserResponse>> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        RegisteredUserResponse user = authService.registerUser(request);

        return ResponseEntity.ok(ApiResponse.success(USER_REGISTRATION_SUCCESSFUL, user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity.ok(ApiResponse.success(LOGIN_SUCCESSFUL, loginResponse));
    }
}
