package com.example.crud_sample.service;

import com.example.crud_sample.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    public List<User> getAll() {
        return users.values().stream().toList();
    }

    public Optional<User> getById(Long id) {
        if (!users.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(users.get(id));
    }

    public User create(User user) {
        user.setId((long) (users.size() + 1));
        save(user);

        return user;
    }

    public void save(User user) {
        users.put(user.getId(), user);
    }
}
