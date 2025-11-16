package com.example.crud_sample.service;

import com.example.crud_sample.config.JwtConfig;
import com.example.crud_sample.dto.request.LoginRequest;
import com.example.crud_sample.dto.request.UserRegistrationRequest;
import com.example.crud_sample.dto.response.LoginResponse;
import com.example.crud_sample.dto.response.RegisteredUserResponse;
import com.example.crud_sample.mapper.UserMapper;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.model.enums.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;

    public RegisteredUserResponse registerUser(UserRegistrationRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        roleService.findByCode(RoleType.USER)
                .ifPresent(role -> user.setRoles(Set.of(role)));

        User registeredUser = userService.create(user);

        return userMapper.toRegisteredUserResponse(registeredUser);
    }

    public LoginResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .expiresAt(LocalDateTime.now()
                        .plus(Duration.ofMillis(jwtConfig.getTokenExpiration())))
                .build();
    }
}
