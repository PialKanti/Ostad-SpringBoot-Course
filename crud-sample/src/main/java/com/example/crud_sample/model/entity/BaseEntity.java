package com.example.crud_sample.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PrePersist
    private void prePersist() {
        if (this instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now();
            if (auditable.getModifiedAt() == null) {
                auditable.setModifiedAt(now);
            }
            if (auditable.getCreatedAt() == null) {
                auditable.setCreatedAt(now);
            }
        }
    }

    @PreUpdate
    private void preUpdate() {
        if (this instanceof Auditable auditable) {
            auditable.setModifiedAt(LocalDateTime.now());
        }
    }
}
