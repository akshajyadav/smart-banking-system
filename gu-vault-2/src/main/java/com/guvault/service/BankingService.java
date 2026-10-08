package com.guvault.service;

import com.guvault.dao.*;
import com.guvault.exception.BankingException;
import com.guvault.model.*;
import com.guvault.util.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class BankingService {
    private final AccountDao accountDao;
    private final BeneficiaryDao beneficiaryDao;
    private final TransactionDao transactionDao;
    private final NotificationDao notificationDao;

    public BankingService(AccountDao accountDao, BeneficiaryDao beneficiaryDao, TransactionDao transactionDao, NotificationDao notificationDao) {
        this.accountDao=accountDao; this.beneficiaryDao=beneficiaryDao; this.transactionDao=transactionDao; this.notificationDao=notificationDao;
    }

    public String transfer(long userId, long beneficiaryId, BigDecimal amount, String note) throws BankingException {
        if (amount == null || amount.signum() <= 0) throw new BankingException("Transfer amount must be greater than zero.");
        if (amount.scale() > 2) throw new BankingException("Transfer amount can have at most two decimal places.");
        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);
            try {
                Account source = accountDao.findByUserId(userId, con);
                if (source == null || !"ACTIVE".equalsIgnoreCase(source.getStatus())) throw new BankingException("Your active source account could not be found.");
                Beneficiary beneficiary = beneficiaryDao.findById(beneficiaryId, userId);
                if (beneficiary == null || !"ACTIVE".equalsIgnoreCase(beneficiary.getStatus())) throw new BankingException("Beneficiary is invalid or inactive.");
                Account destination = accountDao.findByNumber(beneficiary.getAccountNumber(), con);
                if (destination == null || !"ACTIVE".equalsIgnoreCase(destination.getStatus())) throw new BankingException("Destination account is not available in GU-Vault.");
                if (source.getAccountNumber().equals(destination.getAccountNumber())) throw new BankingException("You cannot transfer to your own account.");
                if (source.getBalance().compareTo(amount) < 0) throw new BankingException("Insufficient available balance.");

                BigDecimal newSource = source.getBalance().subtract(amount);
                BigDecimal newDestination = destination.getBalance().add(amount);
                accountDao.updateBalance(source.getId(), newSource, con);
                accountDao.updateBalance(destination.getId(), newDestination, con);

                String ref = "GUV" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
                Transaction tx = new Transaction();
                tx.setReferenceNumber(ref); tx.setSourceAccount(source.getAccountNumber()); tx.setDestinationAccount(destination.getAccountNumber());
                tx.setAmount(amount.setScale(2)); tx.setType("TRANSFER"); tx.setCategory("Transfer");
                tx.setDescription((note == null || note.isBlank()) ? "Fund transfer to " + beneficiary.getDisplayName() : note.trim()); tx.setStatus("SUCCESS");
                transactionDao.create(tx, con);

                Notification n = new Notification(); n.setUserId(userId); n.setTitle("Transfer successful"); n.setMessage("₹" + amount.setScale(2) + " transferred to " + beneficiary.getDisplayName() + ". Ref " + ref); n.setType("TRANSACTION");
                notificationDao.create(n, con);
                if (destination.getUserId() > 0) {
                    Notification recipient = new Notification(); recipient.setUserId(destination.getUserId()); recipient.setTitle("Money received"); recipient.setMessage("₹" + amount.setScale(2) + " credited to your account."); recipient.setType("TRANSACTION"); notificationDao.create(recipient, con);
                }
                con.commit();
                return ref;
            } catch (BankingException e) { con.rollback(); throw e; }
            catch (SQLException e) { con.rollback(); throw new BankingException("Transfer failed. No money was moved.", e); }
            finally { con.setAutoCommit(true); }
        } catch (SQLException e) { throw new BankingException("Unable to access the banking database.", e); }
    }
}
