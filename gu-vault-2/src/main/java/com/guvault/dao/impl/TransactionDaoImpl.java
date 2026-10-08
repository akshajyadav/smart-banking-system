package com.guvault.dao.impl;

import com.guvault.dao.TransactionDao;
import com.guvault.model.Transaction;
import com.guvault.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TransactionDaoImpl implements TransactionDao {
    private static Transaction map(ResultSet rs)throws SQLException{
        Transaction t=new Transaction(); t.setId(rs.getLong("id")); t.setReferenceNumber(rs.getString("reference_number"));
        t.setSourceAccount(rs.getString("source_account")); t.setDestinationAccount(rs.getString("destination_account")); t.setAmount(rs.getBigDecimal("amount"));
        t.setType(rs.getString("transaction_type")); t.setCategory(rs.getString("category")); t.setDescription(rs.getString("description")); t.setStatus(rs.getString("status"));
        Timestamp ts=rs.getTimestamp("transaction_time"); t.setTimestamp(ts==null?null:ts.toLocalDateTime()); return t;
    }
    @Override public long create(Transaction t,Connection con)throws SQLException{
        String sql="INSERT INTO transactions(reference_number,source_account,destination_account,amount,transaction_type,category,description,status) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){ps.setString(1,t.getReferenceNumber());ps.setString(2,t.getSourceAccount());ps.setString(3,t.getDestinationAccount());ps.setBigDecimal(4,t.getAmount());ps.setString(5,t.getType());ps.setString(6,t.getCategory());ps.setString(7,t.getDescription());ps.setString(8,t.getStatus());ps.executeUpdate();try(ResultSet keys=ps.getGeneratedKeys()){keys.next();return keys.getLong(1);}}
    }
    @Override public List<Transaction> findRecentByAccount(String acc,int limit)throws SQLException{
        String sql="SELECT * FROM transactions WHERE (source_account=? OR destination_account=?) ORDER BY transaction_time DESC LIMIT ?"; List<Transaction> list=new ArrayList<>();
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setString(1,acc);ps.setString(2,acc);ps.setInt(3,Math.min(Math.max(limit,1),100));try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}}return list;
    }
    @Override public List<Transaction> searchByAccount(String acc,LocalDate from,LocalDate to,String type,String keyword)throws SQLException{
        StringBuilder sql=new StringBuilder("SELECT * FROM transactions WHERE (source_account=? OR destination_account=?)");
        List<Object> params=new ArrayList<>(List.of(acc,acc));
        if(from!=null){sql.append(" AND DATE(transaction_time) >= ?");params.add(java.sql.Date.valueOf(from));}
        if(to!=null){sql.append(" AND DATE(transaction_time) <= ?");params.add(java.sql.Date.valueOf(to));}
        if(type!=null&&!type.isBlank()&&!type.equalsIgnoreCase("ALL")){sql.append(" AND transaction_type = ?");params.add(type.toUpperCase());}
        if(keyword!=null&&!keyword.isBlank()){sql.append(" AND (description LIKE ? OR category LIKE ? OR reference_number LIKE ?)");String k="%"+keyword+"%";params.add(k);params.add(k);params.add(k);}
        sql.append(" ORDER BY transaction_time DESC LIMIT 250"); List<Transaction> list=new ArrayList<>();
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql.toString())){for(int i=0;i<params.size();i++)ps.setObject(i+1,params.get(i));try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}}return list;
    }
    @Override public Map<String,BigDecimal> spendingByCategory(String acc)throws SQLException{
        String sql="SELECT COALESCE(category,'Other') category, COALESCE(SUM(amount),0) total FROM transactions WHERE source_account=? AND status='SUCCESS' AND transaction_time >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) GROUP BY category ORDER BY total DESC";
        Map<String,BigDecimal> map=new LinkedHashMap<>(); try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setString(1,acc);try(ResultSet rs=ps.executeQuery()){while(rs.next())map.put(rs.getString("category"),rs.getBigDecimal("total"));}}return map;
    }
}
