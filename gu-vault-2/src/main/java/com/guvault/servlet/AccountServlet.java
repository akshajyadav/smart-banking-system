package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.User;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{req.setAttribute("accounts",AppServices.ACCOUNT_DAO.findAllByUserId(u.getId()));req.getRequestDispatcher("/WEB-INF/views/accounts.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}
}
