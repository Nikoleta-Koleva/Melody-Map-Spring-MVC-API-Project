package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
    List<User> getAllUsers();
    void save(User user);
    void deleteUser(int id);
}