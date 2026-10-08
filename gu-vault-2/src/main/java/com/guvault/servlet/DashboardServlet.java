package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.*;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;
        User user=SessionUtil.getUser(req);
        try{
            Account account=AppServices.ACCOUNT_DAO.findByUserId(user.getId());
            List<Transaction> recent=AppServices.TRANSACTION_DAO.findRecentByAccount(account.getAccountNumber(),8);
            List<Notification> notifications=AppServices.NOTIFICATION_DAO.findRecent(user.getId(),5);
            Map<String,BigDecimal> spending=AppServices.TRANSACTION_DAO.spendingByCategory(account.getAccountNumber());
            BigDecimal spent=spending.values().stream().reduce(BigDecimal.ZERO,BigDecimal::add);
            req.setAttribute("account",account);req.setAttribute("recentTransactions",recent);req.setAttribute("notifications",notifications);req.setAttribute("spending",spending);req.setAttribute("monthlySpent",spent);
            req.setAttribute("unreadCount",AppServices.NOTIFICATION_DAO.unreadCount(user.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));
            req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req,resp);
        }catch(SQLException e){throw new ServletException("Unable to load dashboard data.",e);}
    }
}
