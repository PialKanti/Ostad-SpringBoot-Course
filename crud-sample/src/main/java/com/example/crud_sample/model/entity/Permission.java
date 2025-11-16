package com.example.crud_sample.model.entity;

import com.example.crud_sample.model.enums.PermissionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private PermissionType code;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
