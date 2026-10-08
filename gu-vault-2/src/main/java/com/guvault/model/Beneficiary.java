package com.guvault.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Beneficiary implements Serializable {
    private long id;
    private long userId;
    private String beneficiaryName;
    private String accountNumber;
    private String ifscCode;
    private String nickname;
    private String status;
    private LocalDateTime createdAt;

    public Beneficiary() { }

    public Beneficiary(long id, long userId, String beneficiaryName, String accountNumber,
                       String ifscCode, String nickname, String status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.beneficiaryName = beneficiaryName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.nickname = nickname;
        this.status = status;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getDisplayName() {
        return nickname != null && !nickname.isBlank() ? nickname : beneficiaryName;
    }

    public String getMaskedAccountNumber() {
        if (accountNumber == null || accountNumber.length() < 4) return "••••";
        return "••••" + accountNumber.substring(accountNumber.length() - 4);
    }
}
