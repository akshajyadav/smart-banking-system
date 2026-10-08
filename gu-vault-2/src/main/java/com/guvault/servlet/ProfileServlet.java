package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.PasswordUtil;
import com.guvault.util.SessionUtil;
import com.guvault.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{User fresh=AppServices.USER_DAO.findById(u.getId());req.getSession().setAttribute(SessionUtil.USER,fresh);req.setAttribute("user",fresh);req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/profile");return;}User u=SessionUtil.getUser(req);try{String action=req.getParameter("action");if("profile".equals(action)){String name=ValidationUtil.required(req.getParameter("fullName"),"Full name");String email=ValidationUtil.required(req.getParameter("email"),"Email");String phone=ValidationUtil.required(req.getParameter("phone"),"Phone");ValidationUtil.email(email);ValidationUtil.phone(phone);AppServices.USER_DAO.updateProfile(u.getId(),name,email,phone);SessionUtil.success(req,"Profile updated.");}else if("password".equals(action)){String current=req.getParameter("currentPassword"),next=req.getParameter("newPassword");if(!PasswordUtil.verify(current,u.getPasswordHash()))throw new IllegalArgumentException("Current password is incorrect.");if(next==null||next.length()<8)throw new IllegalArgumentException("New password must contain at least 8 characters.");AppServices.USER_DAO.updatePassword(u.getId(),PasswordUtil.hash(next));SessionUtil.success(req,"Password changed successfully.");}}catch(Exception e){SessionUtil.error(req,e.getMessage()==null?"Unable to update profile.":e.getMessage());}resp.sendRedirect(req.getContextPath()+"/profile");}
}
