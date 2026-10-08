package com.guvault.service;

import com.guvault.dao.AccountDao;
import com.guvault.dao.DepositDao;
import com.guvault.dao.NotificationDao;
import com.guvault.dao.TransactionDao;
import com.guvault.exception.BankingException;
import com.guvault.model.*;
import com.guvault.util.DB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DepositService {
    private final AccountDao accountDao; private final DepositDao depositDao; private final TransactionDao transactionDao; private final NotificationDao notificationDao;
    public DepositService(AccountDao a,DepositDao d,TransactionDao t,NotificationDao n){accountDao=a;depositDao=d;transactionDao=t;notificationDao=n;}
    public List<Deposit> list(long userId)throws BankingException{try{return depositDao.findByUserId(userId);}catch(SQLException e){throw new BankingException("Unable to load deposits.",e);}}
    public void create(long userId,String type,BigDecimal amount,BigDecimal rate,int months)throws BankingException{
        if(amount==null||amount.compareTo(BigDecimal.valueOf(1000))<0)throw new BankingException("Minimum deposit amount is ₹1,000.");
        if(months<3||months>60)throw new BankingException("Tenure must be between 3 and 60 months.");
        try(Connection con=DB.getConnection()){
            con.setAutoCommit(false);try{
                Account a=accountDao.findByUserId(userId, con);if(a==null||a.getBalance().compareTo(amount)<0)throw new BankingException("Insufficient balance for this deposit.");
                accountDao.updateBalance(a.getId(),a.getBalance().subtract(amount),con);
                BigDecimal maturity=amount.add(amount.multiply(rate).multiply(BigDecimal.valueOf(months)).divide(BigDecimal.valueOf(1200),2,RoundingMode.HALF_UP));
                Deposit d=new Deposit();d.setUserId(userId);d.setAccountId(a.getId());d.setDepositType(type);d.setPrincipal(amount.setScale(2));d.setInterestRate(rate);d.setTenureMonths(months);d.setStartDate(LocalDate.now());d.setMaturityDate(LocalDate.now().plusMonths(months));d.setMaturityAmount(maturity);d.setStatus("ACTIVE");depositDao.create(d,con);
                String ref="DEP"+UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();Transaction tx=new Transaction();tx.setReferenceNumber(ref);tx.setSourceAccount(a.getAccountNumber());tx.setAmount(amount);tx.setType("DEPOSIT");tx.setCategory("Savings");tx.setDescription(type+" opened for "+months+" months");tx.setStatus("SUCCESS");transactionDao.create(tx,con);
                Notification n=new Notification();n.setUserId(userId);n.setTitle("Deposit created");n.setMessage("Your "+type+" of ₹"+amount.setScale(2)+" is now active.");n.setType("SAVINGS");notificationDao.create(n,con);
                con.commit();
            }catch(BankingException e){con.rollback();throw e;}catch(SQLException e){con.rollback();throw new BankingException("Deposit could not be created.",e);}finally{con.setAutoCommit(true);}
        }catch(SQLException e){throw new BankingException("Unable to access the banking database.",e);}
    }
}
