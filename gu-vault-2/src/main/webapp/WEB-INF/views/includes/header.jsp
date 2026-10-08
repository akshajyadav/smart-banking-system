<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<% com.guvault.model.User currentUser = (com.guvault.model.User) session.getAttribute("loggedInUser"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="theme-color" content="#f5f7fb">
    <title>${empty pageTitle ? 'GU-Vault' : pageTitle} · GU-Vault</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=SF+Pro+Display:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
    <script defer src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body class="app-shell">
<div class="ambient ambient-a"></div><div class="ambient ambient-b"></div>
<div class="mobile-scrim" id="mobileScrim"></div>
<aside class="sidebar glass-panel" id="sidebar">
    <div class="brand-row">
        <div class="brand-orb">G</div>
        <div><div class="brand-title">GU-Vault</div><div class="brand-subtitle">Smart Digital Banking</div></div>
    </div>
    <nav class="nav-list" aria-label="Primary navigation">
        <a class="nav-item ${pageKey == 'dashboard' ? 'active' : ''}" href="${pageContext.request.contextPath}/dashboard"><span>⌂</span>Overview</a>
        <a class="nav-item ${pageKey == 'accounts' ? 'active' : ''}" href="${pageContext.request.contextPath}/accounts"><span>▤</span>Accounts</a>
        <a class="nav-item ${pageKey == 'transfer' ? 'active' : ''}" href="${pageContext.request.contextPath}/transfer"><span>⇄</span>Transfer</a>
        <a class="nav-item ${pageKey == 'payments' ? 'active' : ''}" href="${pageContext.request.contextPath}/payments"><span>◫</span>Payments</a>
        <a class="nav-item ${pageKey == 'transactions' ? 'active' : ''}" href="${pageContext.request.contextPath}/transactions"><span>↺</span>Transactions</a>
        <a class="nav-item ${pageKey == 'beneficiaries' ? 'active' : ''}" href="${pageContext.request.contextPath}/beneficiaries"><span>◎</span>Beneficiaries</a>
        <a class="nav-item ${pageKey == 'cards' ? 'active' : ''}" href="${pageContext.request.contextPath}/cards"><span>▣</span>Cards</a>
        <a class="nav-item ${pageKey == 'loans' ? 'active' : ''}" href="${pageContext.request.contextPath}/loans"><span>◇</span>Loans</a>
        <a class="nav-item ${pageKey == 'deposits' ? 'active' : ''}" href="${pageContext.request.contextPath}/deposits"><span>◉</span>Deposits</a>
        <a class="nav-item ${pageKey == 'assistant' ? 'active' : ''}" href="${pageContext.request.contextPath}/assistant"><span>✦</span>Smart Assistant</a>
        <a class="nav-item ${pageKey == 'notifications' ? 'active' : ''}" href="${pageContext.request.contextPath}/notifications"><span>🔔</span>Notifications<c:if test="${unreadCount gt 0}"><b class="nav-badge">${unreadCount}</b></c:if></a>
        <a class="nav-item ${pageKey == 'profile' ? 'active' : ''}" href="${pageContext.request.contextPath}/profile"><span>◌</span>Profile & Security</a>
    </nav>
    <div class="sidebar-bottom">
        <div class="mini-security"><span class="status-dot"></span><div><strong>Protected session</strong><small>Demo banking environment</small></div></div>
        <form action="${pageContext.request.contextPath}/logout" method="post"><button class="logout-button" type="submit">⇥ <span>Sign out</span></button></form>
    </div>
</aside>
<main class="main-content">
    <header class="topbar glass-panel">
        <button class="icon-button mobile-only" id="menuButton" type="button" aria-label="Open navigation">☰</button>
        <div class="topbar-title"><span class="eyebrow">${empty pageEyebrow ? 'Digital banking' : pageEyebrow}</span><h1>${empty pageHeading ? pageTitle : pageHeading}</h1></div>
        <div class="topbar-actions">
            <a class="icon-button" href="${pageContext.request.contextPath}/notifications" aria-label="Notifications">🔔<c:if test="${unreadCount gt 0}"><i></i></c:if></a>
            <a class="profile-chip" href="${pageContext.request.contextPath}/profile"><span class="avatar">${fn:substring(currentUser.fullName,0,1)}</span><span class="profile-name">${currentUser.fullName}</span></a>
        </div>
    </header>
    <section class="page-container">
        <c:if test="${not empty flashSuccess}"><div class="toast inline-toast success"><span>✓</span>${flashSuccess}</div></c:if>
        <c:if test="${not empty flashError}"><div class="toast inline-toast error"><span>!</span>${flashError}</div></c:if>
