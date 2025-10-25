package com.example.crud_sample.service;

import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        if (!userRepository.existsById(id)) {
            return Optional.empty();
        }

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
}
