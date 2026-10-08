<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1.0"><title>Sign in · GU-Vault</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css"></head>
<body class="auth-page"><div class="ambient ambient-a"></div><div class="ambient ambient-b"></div>
<div class="auth-shell glass-panel">
    <div class="brand-lockup"><div class="brand-orb">G</div><div><div class="brand-title">GU-Vault</div><div class="brand-subtitle">Smart Digital Banking</div></div></div>
    <div class="auth-copy"><span class="eyebrow">Secure access</span><h1>Welcome back.</h1><p>Sign in to manage your simulated accounts, transfers, payments and financial insights.</p></div>
    <% String success=(String)request.getAttribute("success"); String error=(String)request.getAttribute("error"); %>
    <% if(success!=null){ %><div class="toast success"><span>✓</span><%=success%></div><% } %>
    <% if(error!=null){ %><div class="toast error"><span>!</span><%=error%></div><% } %>
    <form class="auth-form" method="post" action="${pageContext.request.contextPath}/login" autocomplete="on">
        <input type="hidden" name="csrfToken" value="${csrfToken}">
        <label>Username<input name="username" required autocomplete="username" placeholder="e.g. akshaj.demo"></label>
        <label>Password<div class="input-with-action"><input id="password" type="password" name="password" required autocomplete="current-password" placeholder="Your password"><button type="button" class="input-action" data-toggle-password="password">Show</button></div></label>
        <button class="primary-button full" type="submit">Sign in <span>→</span></button>
    </form>
    <div class="demo-box"><div><strong>Demo account</strong><small>For local seeded data</small></div><div class="demo-credentials"><span>akshaj.demo</span><button type="button" class="copy-button" data-copy="Demo@123">Copy password</button></div></div>
    <div class="auth-foot">New to GU-Vault? <a href="${pageContext.request.contextPath}/register">Create an account</a></div>
</div></body></html>
