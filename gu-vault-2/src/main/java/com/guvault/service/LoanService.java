package com.guvault.service;

import com.guvault.dao.LoanDao;
import com.guvault.exception.BankingException;
import com.guvault.model.Loan;
import com.guvault.util.MoneyUtil;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class LoanService {
    private final LoanDao dao;
    public LoanService(LoanDao dao){this.dao=dao;}
    public BigDecimal emi(BigDecimal principal, BigDecimal rate, int months){return MoneyUtil.calculateEmi(principal,rate,months);}
    public List<Loan> list(long userId)throws BankingException{try{return dao.findByUserId(userId);}catch(SQLException e){throw new BankingException("Unable to load loans.",e);}}
    public void apply(long userId,String type,BigDecimal principal,BigDecimal rate,int months)throws BankingException{if(principal==null||principal.signum()<=0)throw new BankingException("Loan amount must be greater than zero.");if(months<6||months>84)throw new BankingException("Loan tenure must be between 6 and 84 months.");BigDecimal emi=emi(principal,rate,months);Loan l=new Loan();l.setUserId(userId);l.setLoanType(type);l.setPrincipal(principal.setScale(2));l.setInterestRate(rate);l.setTenureMonths(months);l.setEmi(emi);l.setRemainingBalance(principal.setScale(2));l.setStatus("UNDER_REVIEW");try{dao.create(l);}catch(SQLException e){throw new BankingException("Loan application could not be submitted.",e);}}
}
