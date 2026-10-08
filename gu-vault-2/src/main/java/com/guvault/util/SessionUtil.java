package com.guvault.util;

import com.guvault.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionUtil {
    public static final String USER = "loggedInUser";
    public static final String FLASH_SUCCESS = "flashSuccess";
    public static final String FLASH_ERROR = "flashError";

    private SessionUtil() { }

    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object value = session.getAttribute(USER);
        return value instanceof User user ? user : null;
    }

    public static boolean requireAuth(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws java.io.IOException {
        if (getUser(request) != null) return true;
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }

    public static void success(HttpServletRequest request, String message) {
        request.getSession(true).setAttribute(FLASH_SUCCESS, message);
    }

    public static void error(HttpServletRequest request, String message) {
        request.getSession(true).setAttribute(FLASH_ERROR, message);
    }

    public static String consume(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object value = session.getAttribute(key);
        session.removeAttribute(key);
        return value == null ? null : String.valueOf(value);
    }
}
