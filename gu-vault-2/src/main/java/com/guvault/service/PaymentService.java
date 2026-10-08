package com.guvault.service;

import com.guvault.dao.*;
import com.guvault.exception.BankingException;
import com.guvault.model.*;
import com.guvault.util.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentService {
    private final AccountDao accountDao; private final PaymentDao paymentDao; private final TransactionDao transactionDao; private final NotificationDao notificationDao;
    public PaymentService(AccountDao a, PaymentDao p, TransactionDao t, NotificationDao n){accountDao=a;paymentDao=p;transactionDao=t;notificationDao=n;}

    public String payBill(long userId,String category,String biller,BigDecimal amount,LocalDate dueDate)throws BankingException{
        if(amount==null||amount.signum()<=0)throw new BankingException("Payment amount must be greater than zero.");
        try(Connection con=DB.getConnection()){
            con.setAutoCommit(false);
            try{
                Account account=accountDao.findByUserId(userId, con); if(account==null||!"ACTIVE".equalsIgnoreCase(account.getStatus()))throw new BankingException("Active source account not found.");
                if(account.getBalance().compareTo(amount)<0)throw new BankingException("Insufficient available balance.");
                BigDecimal next=account.getBalance().subtract(amount);accountDao.updateBalance(account.getId(),next,con);
                String ref="PAY"+UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();
                paymentDao.createBill(userId,account.getAccountNumber(),biller,category,amount,dueDate,"PAID",con);
                Transaction tx=new Transaction();tx.setReferenceNumber(ref);tx.setSourceAccount(account.getAccountNumber());tx.setAmount(amount);tx.setType("PAYMENT");tx.setCategory(category);tx.setDescription(biller);tx.setStatus("SUCCESS");transactionDao.create(tx,con);
                Notification n=new Notification();n.setUserId(userId);n.setTitle("Payment successful");n.setMessage("₹"+amount.setScale(2)+" paid to "+biller+". Ref "+ref);n.setType("PAYMENT");notificationDao.create(n,con);
                con.commit();return ref;
            }catch(BankingException e){con.rollback();throw e;}catch(SQLException e){con.rollback();throw new BankingException("Payment failed. No money was moved.",e);}finally{con.setAutoCommit(true);}
        }catch(SQLException e){throw new BankingException("Unable to access the banking database.",e);}
    }
}
