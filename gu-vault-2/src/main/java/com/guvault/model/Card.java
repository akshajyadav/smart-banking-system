package com.guvault.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Card implements Serializable {
    private long id;
    private long accountId;
    private String cardNumber;
    private String cardholderName;
    private LocalDate expiryDate;
    private String status;
    private String network;
    private String pinHash;
    private java.math.BigDecimal dailyLimit;

    public Card() { }

    public Card(long id, long accountId, String cardNumber, String cardholderName, LocalDate expiryDate,
                String status, String network, String pinHash, java.math.BigDecimal dailyLimit) {
        this.id = id;
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expiryDate = expiryDate;
        this.status = status;
        this.network = network;
        this.pinHash = pinHash;
        this.dailyLimit = dailyLimit;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getCardholderName() { return cardholderName; }
    public void setCardholderName(String cardholderName) { this.cardholderName = cardholderName; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }
    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }
    public java.math.BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(java.math.BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }

    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) return "•••• •••• ••••";
        return "•••• •••• •••• " + cardNumber.substring(cardNumber.length() - 4);
    }
}
