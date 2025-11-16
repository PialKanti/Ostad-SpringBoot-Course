package com.example.crud_sample.repository;

import com.example.crud_sample.model.entity.Role;
import com.example.crud_sample.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByCode(RoleType roleType);
}
