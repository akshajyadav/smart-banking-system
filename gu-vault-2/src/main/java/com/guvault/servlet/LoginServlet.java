package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.exception.AuthenticationException;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(SessionUtil.getUser(req)!=null){resp.sendRedirect(req.getContextPath()+"/dashboard");return;}
        req.setAttribute("csrfToken", CsrfUtil.getOrCreate(req));
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!CsrfUtil.validate(req)){req.setAttribute("error","Security token expired. Please try again.");doGet(req,resp);return;}
        try{
            String username=req.getParameter("username"), password=req.getParameter("password");
            User user=AppServices.AUTH.login(username,password,req.getRemoteAddr(),req.getHeader("User-Agent"));
            HttpSession session=req.getSession(true);session.setAttribute(SessionUtil.USER,user);session.setMaxInactiveInterval(30*60);
            resp.sendRedirect(req.getContextPath()+"/dashboard");
        }catch(AuthenticationException e){req.setAttribute("error",e.getMessage());doGet(req,resp);}
    }
}
