package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.UserDao;
import com.wileyedge.melodymap.model.User;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;
import com.wileyedge.melodymap.service.exceptions.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    //Constructor used for testing the service layer
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    @Override
    public Optional<User> findById(int id) throws UserNotFoundException {
        Optional<User> user = userDao.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return user;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        Optional<User> userOpt = userDao.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void save(User user) throws UserNotFoundException, DataValidationException {
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new DataValidationException("Password must be at least 8 characters long: " + user.getPassword());
        }
        if (user.getId() == 0) {
            if (userDao.findByUsername(user.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
        } else {
            Optional<User> existingUser = userDao.findById(user.getId());
            if (existingUser.isPresent()) {
                User updatedUser = existingUser.get();
                updatedUser.setUsername(user.getUsername());
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userDao.save(updatedUser);
            } else {
                throw new UserNotFoundException("User not found with id: " + user.getId());
            }
        }
    }

    @Override
    public Optional<Map<String, Object>> loginUser(String username, String password) {
        Optional<User> user = findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("userId", user.get().getId());
            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteUser(int id) throws UserNotFoundException {
        Optional<User> existingUser = userDao.findById(id);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userDao.deleteUser(id);
    }
}