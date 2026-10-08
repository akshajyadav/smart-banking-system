package com.guvault.dao;

import com.guvault.model.Loan;
import java.sql.SQLException;
import java.util.List;

public interface LoanDao {
    List<Loan> findByUserId(long userId) throws SQLException;
    long create(Loan loan) throws SQLException;
}
