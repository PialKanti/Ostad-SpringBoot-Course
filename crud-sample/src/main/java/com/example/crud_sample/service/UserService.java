package com.example.crud_sample.service;

import com.example.crud_sample.dto.request.UserSearchRequest;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.repository.UserRepository;
import com.example.crud_sample.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean deleteById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> search(UserSearchRequest request) {
        Specification<User> spec = Specification.unrestricted();

        if (request.firstName() != null) spec = spec.and(UserSpecification.hasFirstName(request.firstName()));
        if (request.lastName() != null)  spec = spec.and(UserSpecification.hasLastName(request.lastName()));
        if (request.username() != null)  spec = spec.and(UserSpecification.hasUsername(request.username()));
        if (request.phoneNumber() != null) spec = spec.and(UserSpecification.hasPhoneNumber(request.phoneNumber()));
        if (request.gender() != null) spec = spec.and(UserSpecification.hasGender(request.gender()));

        return userRepository.findAll(spec);
    }
}
