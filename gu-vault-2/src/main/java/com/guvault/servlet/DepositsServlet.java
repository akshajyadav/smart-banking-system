package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/deposits")
public class DepositsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{req.setAttribute("deposits",AppServices.DEPOSITS.list(u.getId()));req.setAttribute("account",AppServices.ACCOUNT_DAO.findByUserId(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/deposits.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/deposits");return;}User u=SessionUtil.getUser(req);try{BigDecimal amount=new BigDecimal(req.getParameter("amount"));BigDecimal rate=new BigDecimal(req.getParameter("rate"));int months=Integer.parseInt(req.getParameter("months"));AppServices.DEPOSITS.create(u.getId(),req.getParameter("depositType"),amount,rate,months);SessionUtil.success(req,"Deposit created successfully.");}catch(Exception e){SessionUtil.error(req,e.getMessage()==null?"Deposit could not be created.":e.getMessage());}resp.sendRedirect(req.getContextPath()+"/deposits");}
}
