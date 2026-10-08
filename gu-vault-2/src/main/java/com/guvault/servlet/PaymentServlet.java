package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import com.guvault.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{req.setAttribute("account",AppServices.ACCOUNT_DAO.findByUserId(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/payments.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/payments");return;}
        User u=SessionUtil.getUser(req);try{String category=ValidationUtil.required(req.getParameter("category"),"Category");String biller=ValidationUtil.required(req.getParameter("biller"),"Biller");BigDecimal amount=ValidationUtil.amount(req.getParameter("amount"),"Amount");String dueText=req.getParameter("dueDate");LocalDate due=dueText==null||dueText.isBlank()?null:LocalDate.parse(dueText);String ref=AppServices.PAYMENTS.payBill(u.getId(),category,biller,amount,due);SessionUtil.success(req,"Payment successful. Reference: "+ref);}catch(Exception e){SessionUtil.error(req,e.getMessage()==null?"Payment failed.":e.getMessage());}resp.sendRedirect(req.getContextPath()+"/payments");
    }
}
