USE gu_vault;

INSERT INTO users (id, username, password_hash, full_name, email, phone, role, status) VALUES
(1, 'akshaj.demo', '120000:Zv1qzxlxfPZHdYt9QFulUA==:7DUnU53Cl7zlJiR4T0whClCKGCTeS/yyog/0g2rbzf4=', 'Akshaj Yadav', 'akshaj.demo@guvault.test', '9876543210', 'CUSTOMER', 'ACTIVE'),
(2, 'riya.demo', '120000:dD7FM4rUCoH7+E+/NIt9sA==:fYJnk+D5V05N8hhh+6VbIMqXNtQf6FWn5/iqwrXd3QI=', 'Riya Sharma', 'riya.demo@guvault.test', '9123456780', 'CUSTOMER', 'ACTIVE');

INSERT INTO accounts (id, user_id, account_number, account_type, ifsc_code, balance, currency, status) VALUES
(1, 1, '425012340001', 'Savings Account', 'GUVB0001001', 78245.50, 'INR', 'ACTIVE'),
(2, 2, '425012340002', 'Savings Account', 'GUVB0001001', 45620.00, 'INR', 'ACTIVE');

INSERT INTO beneficiaries (user_id, beneficiary_name, account_number, ifsc_code, nickname, status) VALUES
(1, 'Riya Sharma', '425012340002', 'GUVB0001001', 'Riya', 'ACTIVE');

INSERT INTO transactions (reference_number, source_account, destination_account, amount, transaction_type, category, description, status, transaction_time) VALUES
('GUVDEMO001', '425012340002', '425012340001', 15000.00, 'TRANSFER', 'Transfer', 'Scholarship / money received', 'SUCCESS', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 DAY)),
('GUVDEMO002', '425012340001', NULL, 799.00, 'PAYMENT', 'Mobile', 'Airtel prepaid recharge', 'SUCCESS', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY)),
('GUVDEMO003', '425012340001', NULL, 2300.00, 'PAYMENT', 'Utilities', 'Electricity bill', 'SUCCESS', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY)),
('GUVDEMO004', NULL, '425012340001', 25000.00, 'CREDIT', 'Salary', 'Monthly scholarship / stipend', 'SUCCESS', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 8 DAY)),
('GUVDEMO005', '425012340001', NULL, 4200.00, 'PAYMENT', 'Education', 'Semester fee payment', 'SUCCESS', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 12 DAY));

INSERT INTO bills (user_id, account_number, biller_name, category, amount, due_date, status) VALUES
(1, '425012340001', 'Galgotias University', 'Education', 45000.00, DATE_ADD(CURRENT_DATE, INTERVAL 18 DAY), 'PENDING'),
(1, '425012340001', 'Noida Power Company', 'Utilities', 2300.00, DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY), 'PAID');

INSERT INTO cards (id, account_id, card_number, cardholder_name, expiry_date, status, network, pin_hash, daily_limit) VALUES
(1, 1, '5234567890123456', 'AKSHAJ YADAV', DATE_ADD(CURRENT_DATE, INTERVAL 4 YEAR), 'ACTIVE', 'VISA', '120000:5jLYkQGrcaiOlQgBxmhIvA==:ZB4VO2T1+YbVUCis9a37uEFiTe0lQNsZJpdmwIMtwKY=', 75000.00);

INSERT INTO loans (user_id, loan_type, principal, interest_rate, tenure_months, emi, remaining_balance, status, applied_on) VALUES
(1, 'Education Loan', 250000.00, 9.25, 36, 7997.19, 219420.00, 'ACTIVE', DATE_SUB(CURRENT_DATE, INTERVAL 92 DAY));

INSERT INTO deposits (user_id, account_id, deposit_type, principal, interest_rate, tenure_months, start_date, maturity_date, maturity_amount, status) VALUES
(1, 1, 'Fixed Deposit', 10000.00, 7.10, 12, DATE_SUB(CURRENT_DATE, INTERVAL 40 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 10 MONTH), 10710.00, 'ACTIVE');

INSERT INTO notifications (user_id, title, message, type, is_read, created_at) VALUES
(1, 'Welcome to GU-Vault', 'Your simulated digital banking profile is ready.', 'GENERAL', FALSE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY)),
(1, 'Semester fee due', 'Galgotias University fee of ₹45,000 is due soon.', 'PAYMENT', FALSE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 DAY)),
(1, 'New device sign-in', 'A successful sign-in to your account was recorded.', 'SECURITY', TRUE, DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY));
