package com.guvault.service;

import com.guvault.dao.CardDao;
import com.guvault.exception.BankingException;
import com.guvault.model.Card;
import com.guvault.util.PasswordUtil;
import java.sql.SQLException;
import java.util.List;

public class CardService {
    private final CardDao dao;
    public CardService(CardDao dao){this.dao=dao;}
    public List<Card> cards(long userId)throws BankingException{try{return dao.findByUserId(userId);}catch(SQLException e){throw new BankingException("Unable to load cards.",e);}}
    public void toggle(long userId,long cardId)throws BankingException{try{List<Card> cards=dao.findByUserId(userId);Card target=cards.stream().filter(c->c.getId()==cardId).findFirst().orElse(null);if(target==null)throw new BankingException("Card not found.");String status="FROZEN".equalsIgnoreCase(target.getStatus())?"ACTIVE":"FROZEN";dao.updateStatus(cardId,userId,status);}catch(SQLException e){throw new BankingException("Unable to update card status.",e);}}
    public void changePin(long userId,long cardId,String pin)throws BankingException{if(pin==null||!pin.matches("\\d{4}"))throw new BankingException("PIN must be exactly four digits.");try{dao.updatePin(cardId,userId,PasswordUtil.hash(pin));}catch(SQLException e){throw new BankingException("Unable to update card PIN.",e);}}
}
