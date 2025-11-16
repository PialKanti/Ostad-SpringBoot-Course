package com.example.crud_sample.repository;

import com.example.crud_sample.model.entity.Permission;
import com.example.crud_sample.model.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByCode(PermissionType permissionType);
}
