package com.guvault.dao.impl;

import com.guvault.dao.UserDao;
import com.guvault.model.User;
import com.guvault.util.DB;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private static User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        Timestamp timestamp = rs.getTimestamp("created_at");
        user.setCreatedAt(timestamp == null ? null : timestamp.toLocalDateTime());
        return user;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    @Override
    public User findById(long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    @Override
    public boolean existsByUsername(String username) throws SQLException {
        return exists("SELECT COUNT(*) FROM users WHERE username = ?", username);
    }

    @Override
    public boolean existsByEmail(String email) throws SQLException {
        return exists("SELECT COUNT(*) FROM users WHERE email = ?", email);
    }

    private boolean exists(String sql, String value) throws SQLException {
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1) > 0; }
        }
    }

    @Override
    public long create(User user, String passwordHash, Connection connection) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, full_name, email, phone, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, passwordHash);
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole() == null ? "CUSTOMER" : user.getRole());
            ps.setString(7, user.getStatus() == null ? "ACTIVE" : user.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("User ID was not generated.");
                return keys.getLong(1);
            }
        }
    }

    @Override
    public void updateProfile(long userId, String fullName, String email, String phone) throws SQLException {
        String sql = "UPDATE users SET full_name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fullName); ps.setString(2, email); ps.setString(3, phone); ps.setLong(4, userId); ps.executeUpdate();
        }
    }

    @Override
    public void updatePassword(long userId, String passwordHash) throws SQLException {
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, passwordHash); ps.setLong(2, userId); ps.executeUpdate();
        }
    }
}
