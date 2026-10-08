package com.guvault.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE = Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern ACCOUNT = Pattern.compile("^[0-9]{10,18}$");

    private ValidationUtil() { }

    public static String required(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " is required.");
        return value.trim();
    }

    public static BigDecimal amount(String value, String field) {
        try {
            BigDecimal amount = new BigDecimal(required(value, field));
            if (amount.signum() <= 0) throw new IllegalArgumentException(field + " must be greater than zero.");
            if (amount.scale() > 2) throw new IllegalArgumentException(field + " can have at most two decimal places.");
            return amount.setScale(2);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(field + " must be a valid amount.");
        }
    }

    public static void email(String value) {
        if (!EMAIL.matcher(required(value, "Email")).matches()) throw new IllegalArgumentException("Enter a valid email address.");
    }

    public static void phone(String value) {
        if (!PHONE.matcher(required(value, "Phone")).matches()) throw new IllegalArgumentException("Enter a valid 10-digit Indian mobile number.");
    }

    public static void accountNumber(String value) {
        if (!ACCOUNT.matcher(required(value, "Account number")).matches()) throw new IllegalArgumentException("Enter a valid account number.");
    }
}
