package com.wileyedge.melodymap.controller;

import com.wileyedge.melodymap.model.User;
import com.wileyedge.melodymap.service.UserServiceInterface;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) throws UserNotFoundException {
        Optional<User> user = userService.findById(id);
        return user.orElse(null);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) throws UserNotFoundException, DataValidationException {
        userService.save(user);
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<User> user = userService.findByUsernameAndPassword(username, password);

        Map<String, Object> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("userId", user.get().getId());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) throws UserNotFoundException, DataValidationException {
        if (user.getId() != id) {
            throw new IllegalArgumentException("Path variable ID do not match with the request body ID");
        }
        userService.save(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) throws UserNotFoundException {
        userService.deleteUser(id);
    }
}