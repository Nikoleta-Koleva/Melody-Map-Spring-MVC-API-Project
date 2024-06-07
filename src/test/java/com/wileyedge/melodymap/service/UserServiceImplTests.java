package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.User;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;
import com.wileyedge.melodymap.service.exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTests {
    private UserServiceInterface userService;
    private UserDaoStubImpl userDaoStub;

    // Ensures that each test method in the test class starts with a fresh instance of UserServiceImpl
    // configured with stub implementations of its dependencies (UserDao and PasswordEncoder).
    // dependencies are injected into the UserServiceImpl instance
    @BeforeEach
    public void setUp() {
        userDaoStub = new UserDaoStubImpl();
        PasswordEncoder passwordEncoder = new PasswordEncoderStubImpl();
        userService = new UserServiceImpl(userDaoStub, passwordEncoder);
    }

    // Clean up users
    @AfterEach
    public void cleanUp() {
        cleanUpUsers();
    }

    private void cleanUpUsers() {
        List<User> users = userDaoStub.getAllUsers();
        for (User user : users) {
            userDaoStub.deleteUser(user.getId());
        }
    }

    @Test
    @DisplayName("Find User by Username Service Test")
    public void findUserByUsernameServiceTest() throws UserNotFoundException {
        Optional<User> user = userService.findByUsername("defaultuser");
        assertTrue(user.isPresent());
        assertEquals("defaultuser", user.get().getUsername());
    }

    @Test
    @DisplayName("Find User by Username Service Test With Nonexistent Username")
    public void findUserByUsernameServiceTestWithNonexistentUsername() {
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("nonexistentuser"));
    }

    @Test
    @DisplayName("Find User by Id Service Test")
    public void findUserByIdServiceTest() throws UserNotFoundException {
        Optional<User> user = userService.findById(1);
        assertTrue(user.isPresent());
        assertEquals(1, user.get().getId());
    }

    @Test
    @DisplayName("Find User by Id Service Test With Nonexistent Id")
    public void findUserByIdServiceTestWithNonexistentId() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(99));
    }

    @Test
    @DisplayName("Get All Users Service Test")
    public void getAllUsersServiceTest() {
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("Save User Service Test")
    public void saveUserServiceTest() throws UserNotFoundException {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        assertDoesNotThrow(() -> userService.save(newUser));

        Optional<User> savedUser = userService.findByUsername("newuser");
        assertTrue(savedUser.isPresent());
        assertEquals("newuser", savedUser.get().getUsername());
    }

    @Test
    @DisplayName("Save User Service Test With Existing Username")
    public void saveUserServiceTestWithExistingUsername() {
        User newUser = new User();
        newUser.setUsername("defaultuser");
        newUser.setPassword("newpassword");
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.save(newUser));
    }

    @Test
    @DisplayName("Update User Service Test")
    public void updateUserServiceTest() throws UserNotFoundException {
        User user = new User();
        user.setId(1);
        user.setUsername("updateduser");
        user.setPassword("updatedpassword");
        assertDoesNotThrow(() -> userService.save(user));

        Optional<User> updatedUser = userService.findByUsername("updateduser");
        assertTrue(updatedUser.isPresent());
        assertEquals(1, updatedUser.get().getId());
        assertEquals("updatedpassword", updatedUser.get().getPassword());
    }

    @Test
    @DisplayName("Update User Service Test With Nonexistent User")
    public void updateUserServiceTestWithNonexistentUser() {
        User user = new User();
        user.setId(99);
        user.setUsername("updateduser");
        user.setPassword("updatedpassword");
        assertThrows(UserNotFoundException.class, () -> userService.save(user));
    }

    @Test
    @DisplayName("Delete User Service Test")
    public void deleteUserServiceTest() {
        assertDoesNotThrow(() -> userService.deleteUser(1));
        assertThrows(UserNotFoundException.class, () -> userService.findById(1));
    }

    @Test
    @DisplayName("Delete User Service Test With Nonexistent User")
    public void deleteUserServiceTestWithNonexistentUser() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99));
    }
}
