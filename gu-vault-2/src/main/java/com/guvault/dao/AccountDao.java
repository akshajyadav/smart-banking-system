package com.guvault.dao;

import com.guvault.model.Account;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    Account findByUserId(long userId) throws SQLException;
    Account findByUserId(long userId, Connection connection) throws SQLException;
    Account findById(long accountId) throws SQLException;
    Account findByNumber(String accountNumber, Connection connection) throws SQLException;
    Account findByNumberForUser(String accountNumber, long userId, Connection connection) throws SQLException;
    void updateBalance(long accountId, BigDecimal newBalance, Connection connection) throws SQLException;
    List<Account> findAllByUserId(long userId) throws SQLException;
}
