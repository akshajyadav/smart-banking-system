# GU-Vault Demo Script

Use this as the order for a 5–8 minute demonstration.

## 1. Sign in

Open `/login` and use `akshaj.demo / Demo@123`.

Explain that the credentials are checked server-side and the password is stored as a PBKDF2 hash.

## 2. Dashboard

Show that the balance, recent transactions and notification count come from MySQL-backed data.

## 3. Beneficiary

Open Beneficiaries and add a demo recipient. Explain that the beneficiary belongs to the authenticated user.

## 4. Transfer

Send a small amount. Explain:

```text
Servlet → Service → DAO/JDBC → MySQL transaction → Response → UI
```

Show the changed balance, transaction history and notification.

## 5. Payments

Pay a small simulated bill and show that a payment creates a bill record, transaction and notification.

## 6. Transactions

Use the search/type/date filters and download a CSV statement.

## 7. Cards

Freeze/unfreeze the demo card and demonstrate the PIN update simulation.

## 8. Loans

Calculate an EMI and optionally submit a demo application.

## 9. Deposits

Open a small simulated deposit and explain that the principal is debited inside a database transaction.

## 10. Smart Assistant

Show local financial insights based on recent debit transactions. Be explicit that no external AI service is called.

## 11. Code architecture

Open the package tree and show:

- model
- dao
- service
- servlet
- util
- exception

Finish by opening `database/schema.sql` and pointing out foreign keys and indexes.
