package com.guvault.dao;

import com.guvault.model.Transaction;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionDao {
    long create(Transaction transaction, Connection connection) throws SQLException;
    List<Transaction> findRecentByAccount(String accountNumber, int limit) throws SQLException;
    List<Transaction> searchByAccount(String accountNumber, LocalDate from, LocalDate to, String type, String keyword) throws SQLException;
    Map<String, BigDecimal> spendingByCategory(String accountNumber) throws SQLException;
}
