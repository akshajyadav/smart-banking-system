package com.guvault.dao;

import com.guvault.model.Notification;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface NotificationDao {
    List<Notification> findRecent(long userId, int limit) throws SQLException;
    int unreadCount(long userId) throws SQLException;
    void create(Notification notification, Connection connection) throws SQLException;
    void markRead(long notificationId, long userId) throws SQLException;
    void markAllRead(long userId) throws SQLException;
}
