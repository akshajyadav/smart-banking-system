package com.guvault.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.SecureRandom;
import java.util.Base64;

public final class CsrfUtil {
    private static final String TOKEN = "csrfToken";
    private static final SecureRandom RANDOM = new SecureRandom();
    private CsrfUtil() { }

    public static String getOrCreate(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Object existing = session.getAttribute(TOKEN);
        if (existing instanceof String value && !value.isBlank()) return value;
        byte[] bytes = new byte[24];
        RANDOM.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        session.setAttribute(TOKEN, token);
        return token;
    }

    public static boolean validate(HttpServletRequest request) {
        String expected = getOrCreate(request);
        String actual = request.getParameter(TOKEN);
        return actual != null && java.security.MessageDigest.isEqual(expected.getBytes(), actual.getBytes());
    }
}
