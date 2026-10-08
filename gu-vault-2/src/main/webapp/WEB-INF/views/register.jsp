<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1.0"><title>Create account · GU-Vault</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css"></head>
<body class="auth-page"><div class="ambient ambient-a"></div><div class="ambient ambient-b"></div>
<div class="auth-shell wide glass-panel"><div class="brand-lockup"><div class="brand-orb">G</div><div><div class="brand-title">GU-Vault</div><div class="brand-subtitle">Smart Digital Banking</div></div></div>
<div class="auth-copy"><span class="eyebrow">New customer</span><h1>Create your banking profile.</h1><p>Your account is seeded with simulated savings data after registration so you can explore the product immediately.</p></div>
<% String error=(String)request.getAttribute("error"); if(error!=null){ %><div class="toast error"><span>!</span><%=error%></div><% } %>
<form class="auth-form two-column" method="post" action="${pageContext.request.contextPath}/register"><input type="hidden" name="csrfToken" value="${csrfToken}">
<label>Full name<input name="fullName" required placeholder="Your name"></label><label>Username<input name="username" required minlength="4" maxlength="30" placeholder="Choose a username"></label>
<label>Email<input type="email" name="email" required placeholder="you@example.com"></label><label>Phone<input name="phone" inputmode="numeric" required maxlength="10" placeholder="10-digit mobile"></label>
<label>Password<input type="password" name="password" required minlength="8" placeholder="At least 8 characters"></label><div></div>
<button class="primary-button full span-2" type="submit">Create GU-Vault account <span>→</span></button></form>
<div class="auth-foot">Already registered? <a href="${pageContext.request.contextPath}/login">Sign in</a></div></div></body></html>
