package com.guvault.service;

import com.guvault.dao.LoginActivityDao;
import com.guvault.dao.UserDao;
import com.guvault.exception.AuthenticationException;
import com.guvault.exception.ValidationException;
import com.guvault.model.User;
import com.guvault.util.DB;
import com.guvault.util.PasswordUtil;
import com.guvault.util.ValidationUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {
    private final UserDao userDao;
    private final LoginActivityDao loginActivityDao;

    public AuthService(UserDao userDao, LoginActivityDao loginActivityDao) {
        this.userDao = userDao;
        this.loginActivityDao = loginActivityDao;
    }

    public User login(String username, String password, String ipAddress, String userAgent) throws AuthenticationException {
        try (Connection con = DB.getConnection()) {
            User user = userDao.findByUsername(username);
            if (user == null || !"ACTIVE".equalsIgnoreCase(user.getStatus()) || !PasswordUtil.verify(password, user.getPasswordHash())) {
                if (user != null) loginActivityDao.record(user.getId(), ipAddress, userAgent, "FAILED", con);
                throw new AuthenticationException("Invalid username or password.");
            }
            loginActivityDao.record(user.getId(), ipAddress, userAgent, "SUCCESS", con);
            return user;
        } catch (AuthenticationException e) {
            throw e;
        } catch (SQLException e) {
            throw new AuthenticationException("Unable to connect to the banking service. Check your database configuration.");
        }
    }

    public void register(String username, String password, String fullName, String email, String phone) throws ValidationException {
        try {
            ValidationUtil.required(username, "Username");
            if (password == null || password.length() < 8) throw new ValidationException("Password must contain at least 8 characters.");
            ValidationUtil.required(fullName, "Full name");
            ValidationUtil.email(email);
            ValidationUtil.phone(phone);
            if (!username.matches("^[A-Za-z0-9._-]{4,30}$")) throw new ValidationException("Username must be 4–30 characters using letters, numbers, dot, underscore or hyphen.");

            if (userDao.existsByUsername(username)) throw new ValidationException("That username is already in use.");
            if (userDao.existsByEmail(email)) throw new ValidationException("That email address is already registered.");

            User user = new User();
            user.setUsername(username.trim()); user.setFullName(fullName.trim()); user.setEmail(email.trim()); user.setPhone(phone.trim());
            user.setRole("CUSTOMER"); user.setStatus("ACTIVE");
            String hash = PasswordUtil.hash(password);

            try (Connection con = DB.getConnection()) {
                con.setAutoCommit(false);
                try {
                    long userId = userDao.create(user, hash, con);
                    String accountNumber = String.format("4250%010d", userId);
                    String sql = "INSERT INTO accounts(user_id,account_number,account_type,ifsc_code,balance,currency,status) VALUES(?,?,?,?,50000.00,'INR','ACTIVE')";
                    try (var ps = con.prepareStatement(sql)) {
                        ps.setLong(1, userId); ps.setString(2, accountNumber); ps.setString(3, "Savings Account"); ps.setString(4, "GUVB0001001"); ps.executeUpdate();
                    }
                    con.commit();
                } catch (SQLException e) { con.rollback(); throw e; }
                finally { con.setAutoCommit(true); }
            }
        } catch (ValidationException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        } catch (SQLException e) {
            throw new ValidationException("Registration could not be completed because of a database error.");
        }
    }
}
