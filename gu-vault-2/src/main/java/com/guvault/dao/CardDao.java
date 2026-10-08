package com.guvault.dao;

import com.guvault.model.Card;
import java.sql.SQLException;
import java.util.List;

public interface CardDao {
    List<Card> findByUserId(long userId) throws SQLException;
    void updateStatus(long cardId, long userId, String status) throws SQLException;
    void updatePin(long cardId, long userId, String pinHash) throws SQLException;
}
