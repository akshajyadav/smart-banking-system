package com.guvault.dao;

import com.guvault.model.User;
import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    User findByUsername(String username) throws SQLException;
    User findById(long id) throws SQLException;
    boolean existsByUsername(String username) throws SQLException;
    boolean existsByEmail(String email) throws SQLException;
    long create(User user, String passwordHash, Connection connection) throws SQLException;
    void updateProfile(long userId, String fullName, String email, String phone) throws SQLException;
    void updatePassword(long userId, String passwordHash) throws SQLException;
}
