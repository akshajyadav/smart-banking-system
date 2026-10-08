<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="Overview"/><c:set var="pageHeading" value="Good to see you, ${sessionScope.loggedInUser.fullName}."/><c:set var="pageEyebrow" value="Personal banking"/><c:set var="pageKey" value="dashboard"/>
<jsp:include page="includes/header.jsp"/>
<div class="hero-grid">
    <div class="balance-card glass-panel">
        <div class="card-topline"><div><span class="eyebrow light">Available balance</span><div class="balance-value" id="balanceValue">₹${account.balance}</div></div><button class="ghost-icon" type="button" data-reveal="balanceValue" aria-label="Toggle balance">◉</button></div>
        <div class="account-meta"><span>${account.accountType}</span><span>·</span><span>${account.maskedAccountNumber}</span><span>·</span><span>${account.ifscCode}</span></div>
        <div class="balance-actions"><a href="${pageContext.request.contextPath}/transfer" class="glass-button light-button">Send money <span>↗</span></a><a href="${pageContext.request.contextPath}/payments" class="glass-button light-button">Pay bills <span>↗</span></a><a href="${pageContext.request.contextPath}/statement" class="glass-button subtle-button">Statement ↓</a></div>
    </div>
    <div class="insight-card glass-panel"><div class="section-kicker">30-day insight</div><h2>Your spending pulse</h2><p class="large-number">₹${monthlySpent}</p><p class="muted">Tracked from successful debit transactions in your account.</p><div class="mini-bars"><c:forEach var="entry" items="${spending}" varStatus="s"><div class="mini-bar"><span style="height:${Math.min(100, (entry.value.doubleValue() / (monthlySpent.doubleValue()==0?1:monthlySpent.doubleValue())) * 100)}%"></span></div></c:forEach><c:if test="${empty spending}"><div class="empty-mini">No recent spending yet.</div></c:if></div></div>
</div>

<div class="quick-grid">
    <a class="quick-card glass-panel" href="${pageContext.request.contextPath}/transfer"><div class="quick-icon">⇄</div><div><strong>Transfer money</strong><small>Send to a saved beneficiary</small></div><span>›</span></a>
    <a class="quick-card glass-panel" href="${pageContext.request.contextPath}/beneficiaries"><div class="quick-icon">◎</div><div><strong>Manage beneficiaries</strong><small>Add or remove recipients</small></div><span>›</span></a>
    <a class="quick-card glass-panel" href="${pageContext.request.contextPath}/cards"><div class="quick-icon">▣</div><div><strong>Card controls</strong><small>Freeze, unfreeze or change PIN</small></div><span>›</span></a>
    <a class="quick-card glass-panel" href="${pageContext.request.contextPath}/loans"><div class="quick-icon">◇</div><div><strong>Loans & EMI</strong><small>Explore eligibility and payments</small></div><span>›</span></a>
</div>

<div class="content-grid">
<section class="glass-panel content-card"><div class="section-head"><div><span class="section-kicker">Activity</span><h2>Recent transactions</h2></div><a class="text-link" href="${pageContext.request.contextPath}/transactions">View all →</a></div>
<div class="transaction-list"><c:forEach var="t" items="${recentTransactions}"><div class="transaction-row"><div class="transaction-icon ${t.type == 'TRANSFER' ? 'blue' : t.type == 'PAYMENT' ? 'orange' : 'green'}">${t.type == 'PAYMENT' ? '↗' : t.type == 'TRANSFER' ? '⇄' : '＋'}</div><div class="transaction-main"><strong>${t.description}</strong><small>${t.category} · ${t.timestamp}</small></div><div class="transaction-amount ${t.sourceAccount == account.accountNumber ? 'debit' : 'credit'}">${t.sourceAccount == account.accountNumber ? '-' : '+'}₹${t.amount}</div></div></c:forEach><c:if test="${empty recentTransactions}"><div class="empty-state">No transactions yet.</div></c:if></div></section>
<section class="glass-panel content-card"><div class="section-head"><div><span class="section-kicker">Assistant</span><h2>Smart nudges</h2></div></div><div class="assistant-card"><div class="assistant-orb">✦</div><div><strong>Keep your buffer healthy</strong><p>Based on your recent debit activity, consider keeping at least ₹10,000 as an emergency buffer.</p></div></div><div class="assistant-card"><div class="assistant-orb violet">◌</div><div><strong>Upcoming university fee</strong><p>There is a pending education payment of ₹45,000 in the seeded demo data.</p></div></div><a class="secondary-button full" href="${pageContext.request.contextPath}/transactions">Explore financial activity</a></section>
</div>

<div class="content-grid bottom-grid"><section class="glass-panel content-card"><div class="section-head"><div><span class="section-kicker">Notifications</span><h2>Latest alerts</h2></div><a class="text-link" href="${pageContext.request.contextPath}/notifications">Open inbox →</a></div><div class="notification-list"><c:forEach var="n" items="${notifications}"><div class="notification-row ${n.read ? '' : 'unread'}"><div class="notification-dot"></div><div><strong>${n.title}</strong><p>${n.message}</p><small>${n.createdAt}</small></div></div></c:forEach></div></section>
<section class="glass-panel content-card"><div class="section-head"><div><span class="section-kicker">Accounts</span><h2>Primary account</h2></div><span class="pill success">${account.status}</span></div><div class="account-detail-grid"><div><span>Account type</span><strong>${account.accountType}</strong></div><div><span>Currency</span><strong>${account.currency}</strong></div><div><span>IFSC</span><strong>${account.ifscCode}</strong></div><div><span>Account</span><strong>${account.maskedAccountNumber}</strong></div></div></section></div>
<jsp:include page="includes/footer.jsp"/>
