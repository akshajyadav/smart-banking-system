package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.Account;
import com.guvault.model.User;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{
            Account a=AppServices.ACCOUNT_DAO.findByUserId(u.getId());LocalDate from=parseDate(req.getParameter("from")),to=parseDate(req.getParameter("to"));String type=req.getParameter("type");String q=req.getParameter("q");
            req.setAttribute("account",a);req.setAttribute("transactions",AppServices.TRANSACTION_DAO.searchByAccount(a.getAccountNumber(),from,to,type,q));req.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(req,resp);
        }catch(Exception e){throw new ServletException(e);}
    }
    private LocalDate parseDate(String s){try{return s==null||s.isBlank()?null:LocalDate.parse(s);}catch(Exception e){return null;}}
}
