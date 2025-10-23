package com.example.crud_sample.repository;

import com.example.crud_sample.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
