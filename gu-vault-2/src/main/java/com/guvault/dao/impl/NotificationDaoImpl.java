package com.guvault.dao.impl;

import com.guvault.dao.NotificationDao;
import com.guvault.model.Notification;
import com.guvault.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDaoImpl implements NotificationDao {
    private static Notification map(ResultSet rs)throws SQLException{
        Notification n=new Notification();n.setId(rs.getLong("id"));n.setUserId(rs.getLong("user_id"));n.setTitle(rs.getString("title"));n.setMessage(rs.getString("message"));n.setType(rs.getString("type"));n.setRead(rs.getBoolean("is_read"));Timestamp ts=rs.getTimestamp("created_at");n.setCreatedAt(ts==null?null:ts.toLocalDateTime());return n;
    }
    @Override public List<Notification> findRecent(long userId,int limit)throws SQLException{String sql="SELECT * FROM notifications WHERE user_id=? ORDER BY created_at DESC LIMIT ?";List<Notification> list=new ArrayList<>();try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,userId);p.setInt(2,Math.min(Math.max(limit,1),100));try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}}return list;}
    @Override public int unreadCount(long userId)throws SQLException{String sql="SELECT COUNT(*) FROM notifications WHERE user_id=? AND is_read=FALSE";try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,userId);try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}}}
    @Override public void create(Notification n,Connection c)throws SQLException{String sql="INSERT INTO notifications(user_id,title,message,type,is_read) VALUES(?,?,?,?,FALSE)";try(PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,n.getUserId());p.setString(2,n.getTitle());p.setString(3,n.getMessage());p.setString(4,n.getType());p.executeUpdate();}}
    @Override public void markRead(long id,long userId)throws SQLException{String sql="UPDATE notifications SET is_read=TRUE WHERE id=? AND user_id=?";try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,id);p.setLong(2,userId);p.executeUpdate();}}
    @Override public void markAllRead(long userId)throws SQLException{String sql="UPDATE notifications SET is_read=TRUE WHERE user_id=?";try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,userId);p.executeUpdate();}}
}
