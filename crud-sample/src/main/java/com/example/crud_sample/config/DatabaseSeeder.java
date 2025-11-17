package com.example.crud_sample.config;

import com.example.crud_sample.model.entity.Permission;
import com.example.crud_sample.model.entity.Role;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.model.enums.PermissionType;
import com.example.crud_sample.model.enums.RoleType;
import com.example.crud_sample.repository.PermissionRepository;
import com.example.crud_sample.repository.RoleRepository;
import com.example.crud_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String MODERATOR_USERNAME = "moderator";
    private static final String MODERATOR_PASSWORD = "admin123";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        Map<PermissionType, Permission> permissionsByType = initializePermissions();
        Map<RoleType, Role> rolesByType = initializeRoles(permissionsByType);
        initializeAdminUser(rolesByType.get(RoleType.ADMIN));
        initializeModeratorUser(rolesByType.get(RoleType.MODERATOR));
    }

    private Map<PermissionType, Permission> initializePermissions() {
        Map<PermissionType, Permission> permissionsByType = new EnumMap<>(PermissionType.class);

        Arrays.stream(PermissionType.values()).forEach(type -> {
            Permission permission = permissionRepository.findByCode(type)
                    .orElseGet(() -> {
                        Permission newPermission = Permission.builder()
                                .name(type.getDisplayValue())
                                .code(type)
                                .build();
                        return permissionRepository.save(newPermission);
                    });
            permissionsByType.put(type, permission);
        });

        return permissionsByType;
    }

    private Map<RoleType, Role> initializeRoles(Map<PermissionType, Permission> permissionsByType) {
        Map<RoleType, List<PermissionType>> defaultRolePermissions = Map.of(
                RoleType.ADMIN, List.of(PermissionType.READ, PermissionType.WRITE),
                RoleType.MODERATOR, List.of(PermissionType.READ),
                RoleType.USER, List.of(PermissionType.READ, PermissionType.WRITE)
        );

        Map<RoleType, Role> rolesByType = new EnumMap<>(RoleType.class);

        Arrays.stream(RoleType.values()).forEach(type -> {
            Role role = roleRepository.findByCode(type)
                    .orElseGet(() -> {
                        Role newRole = Role.builder()
                                .name(type.getDisplayValue())
                                .code(type)
                                .permissions(defaultRolePermissions.get(type).stream()
                                        .map(permissionsByType::get)
                                        .collect(Collectors.toSet()))
                                .build();
                        return roleRepository.save(newRole);
                    });
            rolesByType.put(type, role);
        });

        return rolesByType;
    }

    private void initializeAdminUser(Role adminRole) {
        if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
            User admin = User.builder()
                    .firstName("Super")
                    .lastName("Admin")
                    .email("moderator@example.com")
                    .username(ADMIN_USERNAME)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
        }
    }

    private void initializeModeratorUser(Role moderatorRole) {
        if (userRepository.findByUsername(MODERATOR_USERNAME).isEmpty()) {
            User admin = User.builder()
                    .firstName("Moderator")
                    .lastName("Admin")
                    .email("admin@example.com")
                    .username(MODERATOR_USERNAME)
                    .password(passwordEncoder.encode(MODERATOR_PASSWORD))
                    .roles(Set.of(moderatorRole))
                    .build();
            userRepository.save(admin);
        }
    }
}
