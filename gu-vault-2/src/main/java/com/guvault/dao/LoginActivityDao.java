package com.guvault.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface LoginActivityDao {
    void record(long userId, String ipAddress, String userAgent, String status, Connection connection) throws SQLException;
}
