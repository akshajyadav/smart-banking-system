package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.exception.BankingException;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import com.guvault.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);
        try{req.setAttribute("account",AppServices.ACCOUNT_DAO.findByUserId(u.getId()));req.setAttribute("beneficiaries",AppServices.BENEFICIARY_DAO.findByUserId(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/transfer");return;}
        User u=SessionUtil.getUser(req);
        try{long beneficiaryId=Long.parseLong(req.getParameter("beneficiaryId"));BigDecimal amount=ValidationUtil.amount(req.getParameter("amount"),"Transfer amount");String note=req.getParameter("note");String ref=AppServices.BANKING.transfer(u.getId(),beneficiaryId,amount,note);SessionUtil.success(req,"Transfer successful. Reference: "+ref);}
        catch(Exception e){SessionUtil.error(req,e.getMessage()==null?"Transfer could not be completed.":e.getMessage());}
        resp.sendRedirect(req.getContextPath()+"/transfer");
    }
}
