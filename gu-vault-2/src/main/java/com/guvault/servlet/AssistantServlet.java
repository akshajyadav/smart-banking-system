package com.guvault.servlet;

import com.guvault.config.AppServices;
import com.guvault.model.Account;
import com.guvault.model.User;
import com.guvault.util.MoneyUtil;
import com.guvault.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/assistant")
public class AssistantServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        if(!SessionUtil.requireAuth(req,resp))return;User u=SessionUtil.getUser(req);try{
            Account a=AppServices.ACCOUNT_DAO.findByUserId(u.getId());Map<String,BigDecimal> spending=AppServices.TRANSACTION_DAO.spendingByCategory(a.getAccountNumber());BigDecimal total=spending.values().stream().reduce(BigDecimal.ZERO,BigDecimal::add);
            String topCategory=spending.keySet().stream().findFirst().orElse("General spending");BigDecimal top=spending.getOrDefault(topCategory,BigDecimal.ZERO);
            Map<String,String> insights=new LinkedHashMap<>();
            insights.put("Spending snapshot","You spent "+ MoneyUtil.format(total) +" in the last 30 days based on successful debit transactions.");
            if(top.signum()>0) insights.put("Largest category",topCategory+" is currently your largest tracked category at "+MoneyUtil.format(top)+".");
            if(a.getBalance().compareTo(BigDecimal.valueOf(10000))<0) insights.put("Buffer alert","Your available balance is below ₹10,000. Consider maintaining a small emergency buffer."); else insights.put("Buffer health","Your available balance is above the ₹10,000 demo safety buffer.");
            insights.put("Next action","Review your transaction history and set a spending target before making larger transfers.");
            req.setAttribute("account",a);req.setAttribute("insights",insights);req.setAttribute("spending",spending);req.getRequestDispatcher("/WEB-INF/views/assistant.jsp").forward(req,resp);
        }catch(Exception e){throw new ServletException(e);}
    }
}
