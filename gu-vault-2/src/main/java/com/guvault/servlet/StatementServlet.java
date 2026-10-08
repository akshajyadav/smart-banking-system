package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.Account;
import com.guvault.model.Transaction;
import com.guvault.model.User;
import com.guvault.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/statement")
public class StatementServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{Account a=AppServices.ACCOUNT_DAO.findByUserId(u.getId());List<Transaction> tx=AppServices.TRANSACTION_DAO.searchByAccount(a.getAccountNumber(),null,null,"ALL",null);resp.setContentType("text/csv;charset=UTF-8");resp.setHeader("Content-Disposition","attachment; filename=gu-vault-statement.csv");PrintWriter out=resp.getWriter();out.println("Reference,Date,Type,Category,Description,Amount,Source Account,Destination Account,Status");for(Transaction t:tx){out.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\",\"%s\"%n",esc(t.getReferenceNumber()),t.getTimestamp(),esc(t.getType()),esc(t.getCategory()),esc(t.getDescription()),t.getAmount(),esc(t.getSourceAccount()),esc(t.getDestinationAccount()),esc(t.getStatus()));}}catch(Exception e){resp.sendError(500,"Unable to generate statement.");}}
    private String esc(String v){return v==null?"":v.replace("\"","\"\"");}
}
