package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.exception.ValidationException;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req,resp);}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!CsrfUtil.validate(req)){req.setAttribute("error","Security token expired. Please try again.");doGet(req,resp);return;}
        try{
            AppServices.AUTH.register(req.getParameter("username"),req.getParameter("password"),req.getParameter("fullName"),req.getParameter("email"),req.getParameter("phone"));
            SessionUtil.success(req,"Account created successfully. You can now sign in.");resp.sendRedirect(req.getContextPath()+"/login");
        }catch(ValidationException e){req.setAttribute("error",e.getMessage());doGet(req,resp);}
    }
}
