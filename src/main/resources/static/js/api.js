const BASE = 'http://localhost:8080/api';

const api = {
  get: (url) =>
    fetch(BASE + url).then(r => r.ok ? r.json() : Promise.reject(r)),

  post: (url, body) =>
    fetch(BASE + url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    }).then(async r => {
      if (r.ok) return r.json().catch(() => ({}));
      const err = await r.json().catch(() => ({}));
      return Promise.reject(new Error(err.message || 'Error en la operación'));
    }),

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

// ── SESIÓN ──────────────────────────────────────────────
const session = {
  set: (usuario) =>
    localStorage.setItem('usuario', JSON.stringify(usuario)),

  get: () =>
    JSON.parse(localStorage.getItem('usuario') || 'null'),

  setPermisos: (permisos) =>
    localStorage.setItem('permisos', JSON.stringify(permisos)),

  getPermisos: () =>
    JSON.parse(localStorage.getItem('permisos') || '[]'),

  clear: () => {
    localStorage.removeItem('usuario');
    localStorage.removeItem('permisos');
  },

  check: () => {
    const u = session.get();
    if (!u) window.location.href = 'login.html';
    return u;
  },

  esSuperUsuario: () => {
    const u = session.get();
    return u?.rol?.nombre === 'SUPERUSUARIO';
  },

  puedeVer: (url) => {
    if (session.esSuperUsuario()) return true;
    return session.getPermisos().some(p =>
      p.funcionalidad?.url === url && p.puedeVer === 'S');
  },

  puedeInsertar: (url) => {
    if (session.esSuperUsuario()) return true;
    return session.getPermisos().some(p =>
      p.funcionalidad?.url === url && p.puedeInsertar === 'S');
  },

  puedeEditar: (url) => {
    if (session.esSuperUsuario()) return true;
    return session.getPermisos().some(p =>
      p.funcionalidad?.url === url && p.puedeEditar === 'S');
  },

  puedeEliminar: (url) => {
    if (session.esSuperUsuario()) return true;
    return session.getPermisos().some(p =>
      p.funcionalidad?.url === url && p.puedeEliminar === 'S');
  }
};

// ── CARGAR PERMISOS FRESCOS DESDE API ──────────────────
// Se llama en cada página al cargar
async function cargarPermisosFrescos() {
  const u = session.get();
  if (!u) return;
  if (session.esSuperUsuario()) return; // SU tiene todo, no necesita cargar

  try {
    const permisos = await api.get(`/usuarios/permisos-rol/${u.rol.idRol}`);
    session.setPermisos(permisos);
  } catch(e) {
    console.warn('No se pudieron cargar los permisos:', e);
  }
}

// ── PROTEGER PÁGINA ─────────────────────────────────────
async function protegerPagina() {
  const u    = session.get();
  const page = location.pathname.split('/').pop();
  const libre = ['login.html', 'index.html'];

  if (!u) { location.href = 'login.html'; return; }
  if (libre.includes(page)) return;
  if (session.esSuperUsuario()) return;

  // Cargar permisos frescos antes de verificar
  await cargarPermisosFrescos();

  if (!session.puedeVer(page)) {
    alert('No tienes permiso para acceder a este módulo.');
    location.href = 'index.html';
  }
}

// ── SIDEBAR DINÁMICO ────────────────────────────────────
function buildSidebar() {
  const u        = session.get();
  const permisos = session.getPermisos();
  const esSU     = session.esSuperUsuario();

  const secciones = [
    {
      titulo: 'Principal',
      items: [
        { label: 'Dashboard', url: 'index.html', icon: '🏠', libre: true }
      ]
    },
    {
      titulo: 'Mantenimiento',
      items: [
        { label: 'Alumnos',         url: 'alumnos.html',   icon: '👤' },
        { label: 'Aulas',           url: 'aulas.html',     icon: '🏫' },
        { label: 'Conceptos',       url: 'conceptos.html', icon: '📋' },
      ]
    },
    {
      titulo: 'Operaciones',
      items: [
        { label: 'Matrícula', url: 'matricula.html', icon: '📝' },
        { label: 'Pagos',     url: 'pagos.html',     icon: '💰' },
      ]
    },
    {
      titulo: 'Seguridad',
      items: [
        { label: 'Usuarios', url: 'usuarios.html', icon: '👥', soloSU: true },
        { label: 'Permisos', url: 'permisos.html', icon: '🔐', soloSU: true },
      ]
    },
    {
      titulo: 'Reportes',
      items: [
        { label: 'Reportes', url: 'reportes.html', icon: '📊' },
      ]
    }
  ];

  const nav = document.querySelector('.sidebar-nav');
  if (!nav) return;
  nav.innerHTML = '';

  secciones.forEach(seccion => {
    const itemsVisibles = seccion.items.filter(item => {
      if (item.libre)  return true;
      if (item.soloSU) return esSU;
      if (esSU)        return true;
      return permisos.some(p =>
        p.funcionalidad?.url === item.url && p.puedeVer === 'S');
    });

    if (!itemsVisibles.length) return;

    const secDiv = document.createElement('div');
    secDiv.className = 'nav-section';
    secDiv.textContent = seccion.titulo;
    nav.appendChild(secDiv);

    itemsVisibles.forEach(item => {
      const page   = location.pathname.split('/').pop();
      const activo = page === item.url ? 'active' : '';
      const a      = document.createElement('a');
      a.href       = item.url;
      a.className  = activo;
      a.innerHTML  = `<span class="icon">${item.icon}</span> ${item.label}`;
      nav.appendChild(a);
    });
  });
}

// ── CARGAR USUARIO EN SIDEBAR ───────────────────────────
function loadSidebarUser() {
  const u = session.get();
  if (!u) return;
  const av = document.getElementById('sidebar-avatar');
  const nm = document.getElementById('sidebar-name');
  const rl = document.getElementById('sidebar-rol');
  if (av) av.textContent = u.nombreCompleto?.charAt(0).toUpperCase() || 'U';
  if (nm) nm.textContent = u.nombreCompleto;
  if (rl) rl.textContent = u.rol?.nombre || '';
  buildSidebar();
}

// ── APLICAR PERMISOS EN UI ──────────────────────────────
function aplicarPermisosUI() {
  const page      = location.pathname.split('/').pop();
  const btnNuevo  = document.getElementById('btn-nuevo');
  const btnClonar = document.getElementById('btn-clonar');

  if (btnNuevo)
    btnNuevo.style.display =
      session.puedeInsertar(page) ? 'inline-flex' : 'none';

  if (btnClonar)
    btnClonar.style.display =
      session.puedeInsertar(page) ? 'inline-flex' : 'none';
}

// ── TOAST ───────────────────────────────────────────────
function toast(msg, tipo = 'success') {
  const t = document.getElementById('toast');
  if (!t) return;
  t.textContent = msg;
  t.className   = `show ${tipo}`;
  setTimeout(() => t.className = '', 3000);
}