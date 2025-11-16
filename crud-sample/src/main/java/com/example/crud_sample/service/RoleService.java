package com.example.crud_sample.service;

import com.example.crud_sample.model.entity.Role;
import com.example.crud_sample.model.enums.RoleType;
import com.example.crud_sample.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByCode(RoleType roleType) {
        return roleRepository.findByCode(roleType);
    }
}
