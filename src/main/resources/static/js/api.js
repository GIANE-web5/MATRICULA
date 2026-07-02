const BASE = 'http://localhost:8080/api';

const api = {
  get: (url) =>
    fetch(BASE + url).then(r => r.ok ? r.json() : Promise.reject(r)),

  post: (url, body) =>
    fetch(BASE + url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    }).then(r => r.ok ? r.json() : r.json().then(e => Promise.reject(e))),

  put: (url, body) =>
    fetch(BASE + url, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    }).then(async r => {
      if (r.ok) return r.json().catch(() => ({}));
      const err = await r.json().catch(() => ({}));
      return Promise.reject(new Error(err.message || 'Error en la operación'));
    }),

  delete: (url) =>
    fetch(BASE + url, { method: 'DELETE' })
      .then(r => r.ok ? true : Promise.reject(r))
};

// Sesión simple
const session = {
  set: (usuario) => localStorage.setItem('usuario', JSON.stringify(usuario)),
  get: () => JSON.parse(localStorage.getItem('usuario') || 'null'),
  clear: () => localStorage.removeItem('usuario'),
  check: () => {
    const u = session.get();
    if (!u) window.location.href = 'login.html';
    return u;
  }
};

// Toast global
function toast(msg, tipo = 'success') {
  const t = document.getElementById('toast');
  if (!t) return;
  t.textContent = msg;
  t.className = `show ${tipo}`;
  setTimeout(() => t.className = '', 3000);
}

// Render sidebar activo
function setActiveNav() {
  const page = location.pathname.split('/').pop();
  document.querySelectorAll('.sidebar-nav a').forEach(a => {
    if (a.getAttribute('href') === page) a.classList.add('active');
  });
}

// Cargar datos del usuario en sidebar
function loadSidebarUser() {
  const u = session.get();
  if (!u) return;
  const av = document.getElementById('sidebar-avatar');
  const nm = document.getElementById('sidebar-name');
  const rl = document.getElementById('sidebar-rol');
  if (av) av.textContent = u.nombreCompleto?.charAt(0).toUpperCase() || 'U';
  if (nm) nm.textContent = u.nombreCompleto;
  if (rl) rl.textContent = u.rol?.nombre || '';
}