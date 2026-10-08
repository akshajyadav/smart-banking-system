package com.guvault.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public interface PaymentDao {
    long createBill(long userId, String accountNumber, String billerName, String category,
                    BigDecimal amount, LocalDate dueDate, String status, Connection connection) throws SQLException;
}
