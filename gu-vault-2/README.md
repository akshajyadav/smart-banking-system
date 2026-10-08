# GU-Vault — Smart Digital Banking System

GU-Vault is a full-stack **academic banking simulation** built for a GUVI / Galgotias University Java Web project. It is designed to demonstrate a realistic digital-banking user journey while remaining completely isolated from real banking networks and payment rails.

## Why this project exists

The original prototype was a single-page balance demo. GU-Vault replaces that with a structured Java web application using **Servlets, JSP, JDBC and MySQL**, with a service/DAO architecture and a responsive Liquid Glass-inspired interface.

## Scope

The application is intentionally a **simulation**. It does not connect to SBI, NPCI/UPI, card networks, real payment gateways or real customer accounts.

The implemented core includes:

- Customer registration and login
- Password hashing with PBKDF2 + per-password salt
- Session-based authentication
- Account overview and masked account information
- MySQL-persisted beneficiaries
- Atomic simulated account transfers
- Bill/recharge/education payment simulation
- Transaction history with filters
- CSV statement download
- Card view, freeze/unfreeze and PIN change simulation
- Loan calculator and application workflow
- Fixed/recurring deposit workflow
- Notification centre with read/unread state
- Local Smart Financial Assistant based on transaction history
- Responsive Liquid Glass UI

## Technology stack

- Java 17
- Maven
- Jakarta Servlet 6
- Jakarta JSP 3.1
- JSTL 3
- MySQL 8.x
- JDBC
- Apache Tomcat 10.1+
- HTML5 / CSS3 / Vanilla JavaScript

## Architecture

```text
Browser
   |
   v
JSP / HTML / CSS / JavaScript
   |
   v
Servlet Controller Layer
   |
   v
Service Layer (business rules)
   |
   v
DAO Layer (JDBC / SQL)
   |
   v
MySQL
```

The project uses POJO-style model classes, interfaces, custom exceptions, prepared statements, database transactions and session management so the code can be explained against the GUVI Java Web rubric.

## Project structure

```text
GU-Vault/
├── pom.xml
├── README.md
├── .gitignore
├── database/
│   ├── schema.sql
│   └── seed.sql
├── docs/
│   ├── DEMO_SCRIPT.md
│   └── GUVI_RUBRIC_MAPPING.md
└── src/main/
    ├── java/com/guvault/
    │   ├── config/
    │   ├── dao/
    │   ├── exception/
    │   ├── model/
    │   ├── service/
    │   ├── servlet/
    │   └── util/
    └── webapp/
        ├── WEB-INF/
        │   ├── web.xml
        │   └── views/
        ├── css/app.css
        └── js/app.js
```

## Prerequisites

Install:

1. **JDK 17 or newer**
2. **Maven 3.9+**
3. **MySQL 8.x**
4. **Apache Tomcat 10.1+**
5. Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Verify Git:

```bash
git --version
```

## Database setup

Start MySQL and run:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p gu_vault < database/seed.sql
```

Or open the SQL files in MySQL Workbench and execute them in order.

The default local connection is:

```text
jdbc:mysql://localhost:3306/gu_vault?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true
```

The application reads these environment variables:

```text
GUVAULT_DB_URL
GUVAULT_DB_USER
GUVAULT_DB_PASSWORD
```

If you do not set them, local development defaults are used (`root` + empty password). For a machine with a password-protected MySQL root account, set `GUVAULT_DB_PASSWORD` before starting Tomcat.

Example:

```bash
export GUVAULT_DB_USER=root
export GUVAULT_DB_PASSWORD='YOUR_MYSQL_PASSWORD'
```

## Seeded demo credentials

Username:

```text
akshaj.demo
```

Password:

```text
Demo@123
```

A second seeded customer is also present:

```text
riya.demo / Demo@123
```

These credentials are for the simulated local environment only.

## Build

From the project root:

```bash
mvn clean package
```

The WAR is generated as:

```text
target/gu-vault.war
```

## Run with Tomcat

Copy the generated WAR into Tomcat's `webapps` directory:

```bash
cp target/gu-vault.war "$CATALINA_HOME/webapps/"
```

Start Tomcat:

```bash
"$CATALINA_HOME/bin/startup.sh"
```

Then open:

```text
http://localhost:8080/gu-vault/
```

Stop Tomcat:

```bash
"$CATALINA_HOME/bin/shutdown.sh"
```

If `CATALINA_HOME` is not configured, replace it with the full path to your Tomcat installation.

## Run through an IDE

The same Maven WAR project can be imported into IntelliJ IDEA or Eclipse. Configure a local Tomcat 10.1 run configuration and deploy the `gu-vault` artifact.

## Git / GitHub workflow

Create a new empty GitHub repository named `GU-Vault`, then from the project root run:

```bash
git init
git status
git add .
git commit -m "Build full-stack GU-Vault banking system"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/GU-Vault.git
git remote -v
git push -u origin main
```

For future work:

```bash
git status
git add .
git commit -m "Implement <feature>"
git push
```

Never commit a real database password. `.env` and local secret files are ignored by `.gitignore`.

## Testing checklist

- Start MySQL.
- Load `schema.sql`.
- Load `seed.sql`.
- Configure DB credentials.
- Build with Maven.
- Deploy the WAR to Tomcat.
- Log in with the seeded account.
- Verify balance is loaded from MySQL.
- Add a beneficiary.
- Transfer a small amount.
- Check that transaction history changes.
- Check that balance changes.
- Check notifications.
- Pay a small demo bill.
- Freeze/unfreeze the seeded card.
- Change the demo card PIN.
- Calculate an EMI.
- Submit a demo loan application.
- Open a small deposit.
- Download a CSV statement.

## Rubric positioning

The implementation is designed to give the evaluator visible evidence of:

- OOP, inheritance/polymorphism/interfaces through the layered Java design
- Collections / Generics via Java collection types in services and DAOs
- Exception handling through custom banking/validation/authentication exceptions
- Database operation classes and JDBC prepared statements
- Servlet request routing and session management
- Database-backed web integration
- UI/UX and responsive interface design

See `docs/GUVI_RUBRIC_MAPPING.md` for a presentation/viva-oriented mapping.

## Academic disclaimer

GU-Vault is a classroom/hackathon simulation. Never enter real banking passwords, real card numbers or other sensitive financial information.

## Optional one-command database setup

From the project root:

```bash
./scripts/setup-db.sh
```

The script asks MySQL for the password and loads both SQL files in order.
