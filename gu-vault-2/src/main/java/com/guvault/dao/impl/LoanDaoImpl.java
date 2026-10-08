package com.guvault.dao.impl;

import com.guvault.dao.LoanDao;
import com.guvault.model.Loan;
import com.guvault.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDaoImpl implements LoanDao {
    private static Loan map(ResultSet rs)throws SQLException{Loan l=new Loan();l.setId(rs.getLong("id"));l.setUserId(rs.getLong("user_id"));l.setLoanType(rs.getString("loan_type"));l.setPrincipal(rs.getBigDecimal("principal"));l.setInterestRate(rs.getBigDecimal("interest_rate"));l.setTenureMonths(rs.getInt("tenure_months"));l.setEmi(rs.getBigDecimal("emi"));l.setRemainingBalance(rs.getBigDecimal("remaining_balance"));l.setStatus(rs.getString("status"));Date d=rs.getDate("applied_on");l.setAppliedOn(d==null?null:d.toLocalDate());return l;}
    @Override public List<Loan> findByUserId(long userId)throws SQLException{String sql="SELECT * FROM loans WHERE user_id=? ORDER BY applied_on DESC";List<Loan> list=new ArrayList<>();try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,userId);try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}}return list;}
    @Override public long create(Loan l)throws SQLException{String sql="INSERT INTO loans(user_id,loan_type,principal,interest_rate,tenure_months,emi,remaining_balance,status,applied_on) VALUES(?,?,?,?,?,?,?,?,CURRENT_DATE)";try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){p.setLong(1,l.getUserId());p.setString(2,l.getLoanType());p.setBigDecimal(3,l.getPrincipal());p.setBigDecimal(4,l.getInterestRate());p.setInt(5,l.getTenureMonths());p.setBigDecimal(6,l.getEmi());p.setBigDecimal(7,l.getRemainingBalance());p.setString(8,l.getStatus());p.executeUpdate();try(ResultSet k=p.getGeneratedKeys()){k.next();return k.getLong(1);}}}
}
