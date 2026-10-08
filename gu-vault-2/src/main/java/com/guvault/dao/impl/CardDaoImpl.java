package com.guvault.dao.impl;

import com.guvault.dao.CardDao;
import com.guvault.model.Card;
import com.guvault.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {
    private static Card map(ResultSet rs)throws SQLException{Card c=new Card();c.setId(rs.getLong("id"));c.setAccountId(rs.getLong("account_id"));c.setCardNumber(rs.getString("card_number"));c.setCardholderName(rs.getString("cardholder_name"));Date d=rs.getDate("expiry_date");c.setExpiryDate(d==null?null:d.toLocalDate());c.setStatus(rs.getString("status"));c.setNetwork(rs.getString("network"));c.setPinHash(rs.getString("pin_hash"));c.setDailyLimit(rs.getBigDecimal("daily_limit"));return c;}
    @Override public List<Card> findByUserId(long userId)throws SQLException{String sql="SELECT c.* FROM cards c JOIN accounts a ON a.id=c.account_id WHERE a.user_id=? ORDER BY c.id";List<Card> list=new ArrayList<>();try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setLong(1,userId);try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}}return list;}
    @Override public void updateStatus(long cardId,long userId,String status)throws SQLException{String sql="UPDATE cards c JOIN accounts a ON a.id=c.account_id SET c.status=? WHERE c.id=? AND a.user_id=?";try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setString(1,status);ps.setLong(2,cardId);ps.setLong(3,userId);ps.executeUpdate();}}
    @Override public void updatePin(long cardId,long userId,String pinHash)throws SQLException{String sql="UPDATE cards c JOIN accounts a ON a.id=c.account_id SET c.pin_hash=? WHERE c.id=? AND a.user_id=?";try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){ps.setString(1,pinHash);ps.setLong(2,cardId);ps.setLong(3,userId);ps.executeUpdate();}}
}
