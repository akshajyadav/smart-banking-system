# GUVI Review 1 — Rubric Mapping

The supplied Review 1 screenshot lists separate Java GUI and Java Web rubrics. GU-Vault is being built as a Java Web application, so the most relevant web rubric is:

1. Problem Understanding & Solution Design — 8 marks
2. Core Java Concepts — 10 marks
3. Database Integration (JDBC) — 8 marks
4. Servlets & Web Integration — 7 marks

## Evidence in GU-Vault

### Problem Understanding & Solution Design

The project models a digital banking product with authentication, account information, beneficiaries, transfers, payments, statements, cards, loans, deposits and notifications.

### Core Java Concepts

- Encapsulation: Java model classes with private state and accessors.
- Abstraction: DAO and service interfaces.
- Polymorphism: interfaces are implemented by concrete DAO classes and injected into services.
- Exception handling: custom checked exceptions and JDBC handling.
- Collections / Generics: `List`, `Map`, streams and typed DAO APIs.

### Database Integration (JDBC)

- MySQL relational schema.
- Foreign keys and indexes.
- JDBC `Connection` / `PreparedStatement` / `ResultSet` use.
- Explicit commit / rollback in transfer, payment and deposit flows.
- No client-side balance-only simulation.

### Servlets & Web Integration

- Login/register/dashboard and banking operations are mapped to Servlets.
- Session state is handled with `HttpSession`.
- JSP views render server-provided model data.
- Forms use POST for state-changing operations.

## Viva statement

A good one-sentence explanation is:

> “GU-Vault is a MySQL-backed Java web banking simulation using Servlets as controllers, a service layer for business rules, DAO classes for JDBC persistence, JSP for server-rendered views, and a responsive Liquid Glass user interface.”
