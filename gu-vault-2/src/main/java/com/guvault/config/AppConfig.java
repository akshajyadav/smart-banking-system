package com.guvault.config;

public final class AppConfig {
    private AppConfig() { }

    public static final String DB_URL = env("GUVAULT_DB_URL",
            "jdbc:mysql://localhost:3306/gu_vault?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true");
    public static final String DB_USER = env("GUVAULT_DB_USER", "root");
    public static final String DB_PASSWORD = env("GUVAULT_DB_PASSWORD", "");
    public static final int PASSWORD_ITERATIONS = 120_000;
    public static final int SESSION_TIMEOUT_MINUTES = 30;

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }
}
