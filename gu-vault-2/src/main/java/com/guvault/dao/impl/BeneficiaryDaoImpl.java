package com.guvault.dao.impl;

import com.guvault.dao.BeneficiaryDao;
import com.guvault.model.Beneficiary;
import com.guvault.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryDaoImpl implements BeneficiaryDao {
    private static Beneficiary map(ResultSet rs) throws SQLException {
        Beneficiary b = new Beneficiary(); b.setId(rs.getLong("id")); b.setUserId(rs.getLong("user_id"));
        b.setBeneficiaryName(rs.getString("beneficiary_name")); b.setAccountNumber(rs.getString("account_number"));
        b.setIfscCode(rs.getString("ifsc_code")); b.setNickname(rs.getString("nickname")); b.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("created_at"); b.setCreatedAt(ts == null ? null : ts.toLocalDateTime()); return b;
    }
    @Override public List<Beneficiary> findByUserId(long userId) throws SQLException {
        String sql = "SELECT * FROM beneficiaries WHERE user_id = ? ORDER BY created_at DESC"; List<Beneficiary> list = new ArrayList<>();
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { ps.setLong(1,userId); try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}} return list;
    }
    @Override public Beneficiary findById(long id,long userId)throws SQLException{
        String sql="SELECT * FROM beneficiaries WHERE id=? AND user_id=? LIMIT 1";
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setLong(1,id);ps.setLong(2,userId);try(ResultSet rs=ps.executeQuery()){return rs.next()?map(rs):null;}}
    }
    @Override public boolean exists(long userId,String accountNumber)throws SQLException{
        String sql="SELECT COUNT(*) FROM beneficiaries WHERE user_id=? AND account_number=?";
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setLong(1,userId);ps.setString(2,accountNumber);try(ResultSet rs=ps.executeQuery()){rs.next();return rs.getInt(1)>0;}}
    }
    @Override public long create(Beneficiary b)throws SQLException{
        String sql="INSERT INTO beneficiaries(user_id,beneficiary_name,account_number,ifsc_code,nickname,status) VALUES(?,?,?,?,?, 'ACTIVE')";
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){ps.setLong(1,b.getUserId());ps.setString(2,b.getBeneficiaryName());ps.setString(3,b.getAccountNumber());ps.setString(4,b.getIfscCode());ps.setString(5,b.getNickname());ps.executeUpdate();try(ResultSet keys=ps.getGeneratedKeys()){keys.next();return keys.getLong(1);}}
    }
    @Override public void delete(long id,long userId)throws SQLException{
        String sql="DELETE FROM beneficiaries WHERE id=? AND user_id=?"; try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setLong(1,id);ps.setLong(2,userId);ps.executeUpdate();}
    }
}
