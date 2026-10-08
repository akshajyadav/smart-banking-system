package com.guvault.servlet;

import com.guvault.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({"/","/home"})
public class HomeServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{resp.sendRedirect(req.getContextPath()+(SessionUtil.getUser(req)==null?"/login":"/dashboard"));}
}
