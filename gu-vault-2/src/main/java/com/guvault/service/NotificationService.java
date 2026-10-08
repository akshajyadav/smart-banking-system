package com.guvault.service;

import com.guvault.dao.NotificationDao;
import com.guvault.exception.BankingException;
import com.guvault.model.Notification;
import java.sql.SQLException;
import java.util.List;

public class NotificationService {
    private final NotificationDao dao;
    public NotificationService(NotificationDao dao){this.dao=dao;}
    public List<Notification> recent(long userId)throws BankingException{try{return dao.findRecent(userId,100);}catch(SQLException e){throw new BankingException("Unable to load notifications.",e);}}
    public int unread(long userId)throws BankingException{try{return dao.unreadCount(userId);}catch(SQLException e){throw new BankingException("Unable to load notification count.",e);}}
    public void read(long id,long userId)throws BankingException{try{dao.markRead(id,userId);}catch(SQLException e){throw new BankingException("Unable to update notification.",e);}}
    public void readAll(long userId)throws BankingException{try{dao.markAllRead(userId);}catch(SQLException e){throw new BankingException("Unable to update notifications.",e);}}
}
