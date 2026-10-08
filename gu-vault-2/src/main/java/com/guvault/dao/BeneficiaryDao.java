package com.guvault.dao;

import com.guvault.model.Beneficiary;
import java.sql.SQLException;
import java.util.List;

public interface BeneficiaryDao {
    List<Beneficiary> findByUserId(long userId) throws SQLException;
    Beneficiary findById(long id, long userId) throws SQLException;
    boolean exists(long userId, String accountNumber) throws SQLException;
    long create(Beneficiary beneficiary) throws SQLException;
    void delete(long id, long userId) throws SQLException;
}
