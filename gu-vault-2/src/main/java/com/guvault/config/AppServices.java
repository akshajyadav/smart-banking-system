package com.guvault.config;

import com.guvault.dao.*;
import com.guvault.dao.impl.*;
import com.guvault.service.*;

public final class AppServices {
    private AppServices() { }
    public static final UserDao USER_DAO = new UserDaoImpl();
    public static final AccountDao ACCOUNT_DAO = new AccountDaoImpl();
    public static final BeneficiaryDao BENEFICIARY_DAO = new BeneficiaryDaoImpl();
    public static final TransactionDao TRANSACTION_DAO = new TransactionDaoImpl();
    public static final NotificationDao NOTIFICATION_DAO = new NotificationDaoImpl();
    public static final CardDao CARD_DAO = new CardDaoImpl();
    public static final LoanDao LOAN_DAO = new LoanDaoImpl();
    public static final DepositDao DEPOSIT_DAO = new DepositDaoImpl();
    public static final PaymentDao PAYMENT_DAO = new PaymentDaoImpl();
    public static final LoginActivityDao LOGIN_ACTIVITY_DAO = new LoginActivityDaoImpl();

    public static final AuthService AUTH = new AuthService(USER_DAO, LOGIN_ACTIVITY_DAO);
    public static final BankingService BANKING = new BankingService(ACCOUNT_DAO, BENEFICIARY_DAO, TRANSACTION_DAO, NOTIFICATION_DAO);
    public static final PaymentService PAYMENTS = new PaymentService(ACCOUNT_DAO, PAYMENT_DAO, TRANSACTION_DAO, NOTIFICATION_DAO);
    public static final CardService CARDS = new CardService(CARD_DAO);
    public static final LoanService LOANS = new LoanService(LOAN_DAO);
    public static final DepositService DEPOSITS = new DepositService(ACCOUNT_DAO, DEPOSIT_DAO, TRANSACTION_DAO, NOTIFICATION_DAO);
    public static final NotificationService NOTIFICATIONS = new NotificationService(NOTIFICATION_DAO);
}
