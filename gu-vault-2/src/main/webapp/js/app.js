document.addEventListener('DOMContentLoaded', () => {
  const sidebar = document.getElementById('sidebar');
  const scrim = document.getElementById('mobileScrim');
  const menuButton = document.getElementById('menuButton');
  const toggleMenu = () => { sidebar?.classList.toggle('open'); scrim?.classList.toggle('show'); };
  menuButton?.addEventListener('click', toggleMenu);
  scrim?.addEventListener('click', toggleMenu);

  document.querySelectorAll('[data-toggle-password]').forEach(btn => {
    btn.addEventListener('click', () => {
      const input = document.getElementById(btn.dataset.togglePassword);
      if (!input) return;
      input.type = input.type === 'password' ? 'text' : 'password';
      btn.textContent = input.type === 'password' ? 'Show' : 'Hide';
    });
  });

  document.querySelectorAll('[data-reveal]').forEach(btn => {
    btn.addEventListener('click', () => {
      const el = document.getElementById(btn.dataset.reveal);
      if (!el) return;
      const hidden = el.dataset.hidden === 'true';
      if (hidden) { el.textContent = el.dataset.value; el.dataset.hidden = 'false'; }
      else { el.dataset.value = el.textContent; el.textContent = '₹ ••••••••'; el.dataset.hidden = 'true'; }
    });
  });

  document.querySelectorAll('[data-confirm]').forEach(form => {
    form.addEventListener('submit', event => {
      if (!window.confirm(form.dataset.confirm)) event.preventDefault();
    });
  });

  document.querySelectorAll('[data-prefill]').forEach(tile => {
    tile.addEventListener('click', () => {
      const [category, biller] = tile.dataset.prefill.split('|');
      const categoryEl = document.getElementById('paymentCategory');
      const billerEl = document.getElementById('paymentBiller');
      if (categoryEl) categoryEl.value = category;
      if (billerEl) billerEl.value = biller;
    });
  });

  document.querySelectorAll('[data-copy]').forEach(btn => {
    btn.addEventListener('click', async () => {
      try {
        await navigator.clipboard.writeText(btn.dataset.copy);
        btn.textContent = 'Copied';
        setTimeout(() => btn.textContent = 'Copy password', 1500);
      } catch (_) { btn.textContent = 'Demo@123'; }
    });
  });
});
