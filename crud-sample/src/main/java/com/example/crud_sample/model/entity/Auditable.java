package com.example.crud_sample.model.entity;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime dateTime);

    LocalDateTime getModifiedAt();

    void setModifiedAt(LocalDateTime dateTime);
}
