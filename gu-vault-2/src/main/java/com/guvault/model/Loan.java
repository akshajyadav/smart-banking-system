package com.guvault.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan implements Serializable {
    private long id;
    private long userId;
    private String loanType;
    private BigDecimal principal;
    private BigDecimal interestRate;
    private int tenureMonths;
    private BigDecimal emi;
    private BigDecimal remainingBalance;
    private String status;
    private LocalDate appliedOn;

    public Loan() { }

    public Loan(long id, long userId, String loanType, BigDecimal principal, BigDecimal interestRate,
                int tenureMonths, BigDecimal emi, BigDecimal remainingBalance, String status, LocalDate appliedOn) {
        this.id = id;
        this.userId = userId;
        this.loanType = loanType;
        this.principal = principal;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.emi = emi;
        this.remainingBalance = remainingBalance;
        this.status = status;
        this.appliedOn = appliedOn;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }
    public BigDecimal getPrincipal() { return principal; }
    public void setPrincipal(BigDecimal principal) { this.principal = principal; }
    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }
    public BigDecimal getEmi() { return emi; }
    public void setEmi(BigDecimal emi) { this.emi = emi; }
    public BigDecimal getRemainingBalance() { return remainingBalance; }
    public void setRemainingBalance(BigDecimal remainingBalance) { this.remainingBalance = remainingBalance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getAppliedOn() { return appliedOn; }
    public void setAppliedOn(LocalDate appliedOn) { this.appliedOn = appliedOn; }
}
