package com.guvault.dao;

import com.guvault.model.Deposit;
import java.sql.SQLException;
import java.util.List;

public interface DepositDao {
    List<Deposit> findByUserId(long userId) throws SQLException;
    long create(Deposit deposit, java.sql.Connection connection) throws SQLException;
}
