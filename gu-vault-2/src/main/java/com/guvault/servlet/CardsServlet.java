package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.User;
import com.guvault.util.CsrfUtil;
import com.guvault.util.PasswordUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cards")
public class CardsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{req.setAttribute("cards",AppServices.CARDS.cards(u.getId()));req.setAttribute("csrfToken",CsrfUtil.getOrCreate(req));req.setAttribute("flashSuccess",SessionUtil.consume(req,SessionUtil.FLASH_SUCCESS));req.setAttribute("flashError",SessionUtil.consume(req,SessionUtil.FLASH_ERROR));req.getRequestDispatcher("/WEB-INF/views/cards.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{if(!SessionUtil.requireAuth(req,resp))return;if(!CsrfUtil.validate(req)){SessionUtil.error(req,"Security token expired.");resp.sendRedirect(req.getContextPath()+"/cards");return;}User u=SessionUtil.getUser(req);try{long id=Long.parseLong(req.getParameter("cardId"));String action=req.getParameter("action");if("toggle".equals(action))AppServices.CARDS.toggle(u.getId(),id);else if("pin".equals(action))AppServices.CARDS.changePin(u.getId(),id,req.getParameter("pin"));SessionUtil.success(req,"Card updated successfully.");}catch(Exception e){SessionUtil.error(req,e.getMessage());}resp.sendRedirect(req.getContextPath()+"/cards");}
}
