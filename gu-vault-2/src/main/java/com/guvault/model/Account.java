package com.guvault.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
    private long id;
    private long userId;
    private String accountNumber;
    private String accountType;
    private String ifscCode;
    private BigDecimal balance;
    private String currency;
    private String status;

    public Account() { }

    public Account(long id, long userId, String accountNumber, String accountType, String ifscCode,
                   BigDecimal balance, String currency, String status) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.ifscCode = ifscCode;
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMaskedAccountNumber() {
        if (accountNumber == null || accountNumber.length() < 4) return "••••";
        return "•••• •••• •••• " + accountNumber.substring(accountNumber.length() - 4);
    }
}
