package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{req.setAttribute("notifications",AppServices.NOTIFICATIONS.recent(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.getRequestDispatcher("/WEB-INF/views/notifications.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){resp.sendRedirect(req.getContextPath()+"/notifications");return;}User u=SessionUtil.getUser(req);try{if("all".equals(req.getParameter("action")))AppServices.NOTIFICATIONS.readAll(u.getId());else AppServices.NOTIFICATIONS.read(Long.parseLong(req.getParameter("id")),u.getId());}catch(Exception ignored){}resp.sendRedirect(req.getContextPath()+"/notifications");}
}
