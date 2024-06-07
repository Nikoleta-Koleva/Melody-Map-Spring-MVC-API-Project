package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.User;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<User> findByUsername(String username) throws UserNotFoundException;
    Optional<User> findById(int id) throws UserNotFoundException;
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<Map<String, Object>> loginUser(String username, String password);
    List<User> getAllUsers(); // Renamed this method
    void save(User user) throws UserNotFoundException, DataValidationException;
    void deleteUser(int id) throws UserNotFoundException; // Renamed this method
}
