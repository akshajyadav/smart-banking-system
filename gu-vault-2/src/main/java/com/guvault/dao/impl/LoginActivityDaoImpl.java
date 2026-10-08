package com.guvault.dao.impl;

import com.guvault.dao.LoginActivityDao;

import java.sql.*;

public class LoginActivityDaoImpl implements LoginActivityDao {
    @Override public void record(long userId,String ip,String agent,String status,Connection c)throws SQLException{
        String sql="INSERT INTO login_activity(user_id,ip_address,user_agent,status) VALUES(?,?,?,?)";
        try(PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,userId);p.setString(2,ip);p.setString(3,agent);p.setString(4,status);p.executeUpdate();}
    }
}
