package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.dao.mappers.UserMapper;
import com.wileyedge.melodymap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), username);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}