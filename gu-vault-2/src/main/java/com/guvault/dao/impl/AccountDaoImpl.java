package com.guvault.dao.impl;

import com.guvault.dao.AccountDao;
import com.guvault.model.Account;
import com.guvault.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static Account map(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setId(rs.getLong("id")); a.setUserId(rs.getLong("user_id")); a.setAccountNumber(rs.getString("account_number"));
        a.setAccountType(rs.getString("account_type")); a.setIfscCode(rs.getString("ifsc_code"));
        a.setBalance(rs.getBigDecimal("balance")); a.setCurrency(rs.getString("currency")); a.setStatus(rs.getString("status"));
        return a;
    }

    @Override public Account findByUserId(long userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ? ORDER BY id LIMIT 1";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId); try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    @Override public Account findByUserId(long userId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ? ORDER BY id LIMIT 1 FOR UPDATE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) { ps.setLong(1, userId); try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; } }
    }
    @Override public Account findById(long accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ? LIMIT 1";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, accountId); try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    @Override public Account findByNumber(String accountNumber, Connection connection) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountNumber); try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    @Override public Account findByNumberForUser(String accountNumber, long userId, Connection connection) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND user_id = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountNumber); ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    @Override public void updateBalance(long accountId, BigDecimal newBalance, Connection connection) throws SQLException {
        String sql = "UPDATE accounts SET balance = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) { ps.setBigDecimal(1, newBalance); ps.setLong(2, accountId); ps.executeUpdate(); }
    }
    @Override public List<Account> findAllByUserId(long userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ? ORDER BY id";
        List<Account> result = new ArrayList<>();
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId); try (ResultSet rs = ps.executeQuery()) { while (rs.next()) result.add(map(rs)); }
        }
        return result;
    }
}
