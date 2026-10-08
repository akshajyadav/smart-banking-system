package com.guvault.dao.impl;

import com.guvault.dao.PaymentDao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class PaymentDaoImpl implements PaymentDao {
    @Override public long createBill(long userId,String accountNumber,String billerName,String category,BigDecimal amount,LocalDate dueDate,String status,Connection c)throws SQLException{
        String sql="INSERT INTO bills(user_id,account_number,biller_name,category,amount,due_date,status) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){p.setLong(1,userId);p.setString(2,accountNumber);p.setString(3,billerName);p.setString(4,category);p.setBigDecimal(5,amount);p.setDate(6,dueDate==null?null:Date.valueOf(dueDate));p.setString(7,status);p.executeUpdate();try(ResultSet k=p.getGeneratedKeys()){k.next();return k.getLong(1);}}
    }
}
