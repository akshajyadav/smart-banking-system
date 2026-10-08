package com.guvault.dao.impl;

import com.guvault.dao.DepositDao;
import com.guvault.model.Deposit;
import com.guvault.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepositDaoImpl implements DepositDao {
    private static Deposit map(ResultSet rs)throws SQLException{Deposit d=new Deposit();d.setId(rs.getLong("id"));d.setUserId(rs.getLong("user_id"));d.setAccountId(rs.getLong("account_id"));d.setDepositType(rs.getString("deposit_type"));d.setPrincipal(rs.getBigDecimal("principal"));d.setInterestRate(rs.getBigDecimal("interest_rate"));d.setTenureMonths(rs.getInt("tenure_months"));Date s=rs.getDate("start_date"),m=rs.getDate("maturity_date");d.setStartDate(s==null?null:s.toLocalDate());d.setMaturityDate(m==null?null:m.toLocalDate());d.setMaturityAmount(rs.getBigDecimal("maturity_amount"));d.setStatus(rs.getString("status"));return d;}
    @Override public List<Deposit> findByUserId(long userId)throws SQLException{String sql="SELECT * FROM deposits WHERE user_id=? ORDER BY start_date DESC";List<Deposit> list=new ArrayList<>();try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setLong(1,userId);try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}}return list;}
    @Override public long create(Deposit d,Connection c)throws SQLException{String sql="INSERT INTO deposits(user_id,account_id,deposit_type,principal,interest_rate,tenure_months,start_date,maturity_date,maturity_amount,status) VALUES(?,?,?,?,?,?,CURRENT_DATE,DATE_ADD(CURRENT_DATE,INTERVAL ? MONTH),?,'ACTIVE')";try(PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){p.setLong(1,d.getUserId());p.setLong(2,d.getAccountId());p.setString(3,d.getDepositType());p.setBigDecimal(4,d.getPrincipal());p.setBigDecimal(5,d.getInterestRate());p.setInt(6,d.getTenureMonths());p.setInt(7,d.getTenureMonths());p.setBigDecimal(8,d.getMaturityAmount());p.executeUpdate();try(ResultSet k=p.getGeneratedKeys()){k.next();return k.getLong(1);}}}
}
