package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.TestApplicationConfiguration;
import com.wileyedge.melodymap.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class UserDaoImplTests {

    @Autowired
    private UserDaoImpl userDao;

    // Clean up users
    @BeforeEach
    public void setUp() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getId());
        }
    }

    // Clean up users
    @AfterEach
    public void cleanUp() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getId());
        }
    }

    @Test
    public void testSaveAndFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        userDao.save(user);

        Optional<User> fromDaoOptional = userDao.findByUsername("testuser");
        assertTrue(fromDaoOptional.isPresent());

        User fromDao = fromDaoOptional.get();
        assertEquals(user.getUsername(), fromDao.getUsername());
        assertEquals(user.getPassword(), fromDao.getPassword());
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        userDao.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        userDao.save(user2);

        List<User> users = userDao.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user1")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user2")));
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        userDao.save(user);

        Optional<User> fromDaoOptional = userDao.findByUsername("testuser");
        assertTrue(fromDaoOptional.isPresent());

        User fromDao = fromDaoOptional.get();
        userDao.deleteUser(fromDao.getId());

        Optional<User> deletedUserOptional = userDao.findByUsername("testuser");
        assertFalse(deletedUserOptional.isPresent());
    }
}
