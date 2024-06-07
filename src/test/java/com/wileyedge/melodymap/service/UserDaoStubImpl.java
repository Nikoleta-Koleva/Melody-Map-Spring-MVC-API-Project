package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.UserDao;
import com.wileyedge.melodymap.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDaoStubImpl implements UserDao {

    private Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    public UserDaoStubImpl() {
        // Initialize with a default user
        User defaultUser = new User();
        defaultUser.setId(nextId++);
        defaultUser.setUsername("defaultuser");
        defaultUser.setPassword("password123");
        users.put(defaultUser.getId(), defaultUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(nextId++);
        }
        users.put(user.getId(), user);
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }
}
