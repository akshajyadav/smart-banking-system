package com.guvault.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyUtil {
    private MoneyUtil() { }

    public static BigDecimal normalize(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO.setScale(2) : amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static String format(BigDecimal amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(normalize(amount));
    }

    public static BigDecimal calculateEmi(BigDecimal principal, BigDecimal annualRate, int months) {
        if (months <= 0 || principal == null || principal.signum() <= 0) return BigDecimal.ZERO.setScale(2);
        BigDecimal monthly = annualRate.divide(BigDecimal.valueOf(1200), 12, RoundingMode.HALF_UP);
        if (monthly.signum() == 0) return principal.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
        double p = principal.doubleValue();
        double r = monthly.doubleValue();
        double emi = p * r * Math.pow(1 + r, months) / (Math.pow(1 + r, months) - 1);
        return BigDecimal.valueOf(emi).setScale(2, RoundingMode.HALF_UP);
    }
}
