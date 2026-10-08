package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.exception.BankingException;
import com.guvault.model.Beneficiary;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import com.guvault.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/beneficiaries")
public class BeneficiaryServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);
        try{req.setAttribute("beneficiaries",AppServices.BENEFICIARY_DAO.findByUserId(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/beneficiaries.jsp").forward(req,resp);}catch(SQLException e){throw new ServletException(e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/beneficiaries");return;}
        User u=SessionUtil.getUser(req);String action=req.getParameter("action");
        try{
            if("add".equals(action)){String name=ValidationUtil.required(req.getParameter("beneficiaryName"),"Beneficiary name");String acc=ValidationUtil.required(req.getParameter("accountNumber"),"Account number");ValidationUtil.accountNumber(acc);String ifsc=ValidationUtil.required(req.getParameter("ifscCode"),"IFSC code");String nick=req.getParameter("nickname");if(AppServices.BENEFICIARY_DAO.exists(u.getId(),acc))throw new BankingException("That beneficiary account is already saved.");Beneficiary b=new Beneficiary();b.setUserId(u.getId());b.setBeneficiaryName(name);b.setAccountNumber(acc);b.setIfscCode(ifsc.toUpperCase());b.setNickname(nick);AppServices.BENEFICIARY_DAO.create(b);SessionUtil.success(req,"Beneficiary added successfully.");}
            else if("delete".equals(action)){long id=Long.parseLong(req.getParameter("id"));AppServices.BENEFICIARY_DAO.delete(id,u.getId());SessionUtil.success(req,"Beneficiary removed.");}
        }catch(Exception e){SessionUtil.error(req,e.getMessage()==null?"Unable to update beneficiaries.":e.getMessage());}
        resp.sendRedirect(req.getContextPath()+"/beneficiaries");
    }
}
