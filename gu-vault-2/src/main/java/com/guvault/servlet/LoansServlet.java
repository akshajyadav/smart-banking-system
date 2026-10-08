package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.exception.BankingException;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.MoneyUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/loans")
public class LoansServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{req.setAttribute("loans",AppServices.LOANS.list(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/loans.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/loans");return;}User u=SessionUtil.getUser(req);try{String action=req.getParameter("action");BigDecimal principal=new BigDecimal(req.getParameter("principal"));BigDecimal rate=new BigDecimal(req.getParameter("rate"));int months=Integer.parseInt(req.getParameter("months"));if("calculate".equals(action)){req.getSession(true).setAttribute("emi",MoneyUtil.calculateEmi(principal,rate,months));}else if("apply".equals(action)){AppServices.LOANS.apply(u.getId(),req.getParameter("loanType"),principal,rate,months);SessionUtil.success(req,"Loan application submitted for review.");}}catch(Exception e){SessionUtil.error(req,e.getMessage()==null?"Loan operation failed.":e.getMessage());}resp.sendRedirect(req.getContextPath()+"/loans");}
}
