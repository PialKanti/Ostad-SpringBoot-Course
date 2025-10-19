package com.example.crud_sample.service;

import com.example.crud_sample.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {
    private Map<Long, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    public List<User> getAll() {
        return users.values().stream().toList();
    }

    public User getById(Long id) {
        return users.get(id);
    }

    public User create(User user) {
        user.setId((long) (users.size() + 1));
        users.put(user.getId(), user);

        return user;
    }
}
