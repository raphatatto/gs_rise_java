const endpoints = {
  curriculos: '/api/v1/curriculos',
  cursos: '/api/v1/cursos',
  bemEstar: '/api/v1/bemestar',
  trilhas: '/api/v1/trilhas',
  objetivos: '/api/v1/objetivos'
};

function setStatus(elementId, message, isError = false) {
  const el = document.getElementById(elementId);
  if (!el) return;
  el.textContent = message;
  el.classList.toggle('error', isError);
  el.classList.toggle('success', !isError);
  el.style.display = 'block';
}

async function sendJson(url, method, body) {
  const response = await fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: body ? JSON.stringify(body) : undefined
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || `${method} ${url} falhou`);
  }
  if (response.status !== 204) {
    return response.json();
  }
  return null;
}

function renderTable(containerId, items, columns) {
  const container = document.getElementById(containerId);
  if (!container) return;
  container.innerHTML = '';

  if (!items || items.length === 0) {
    container.textContent = 'Nenhum registro encontrado.';
    return;
  }

  const table = document.createElement('table');
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');

  columns.forEach(col => {
    const th = document.createElement('th');
    th.textContent = col.label;
    headerRow.appendChild(th);
  });
  thead.appendChild(headerRow);
  table.appendChild(thead);

  const tbody = document.createElement('tbody');
  items.forEach(item => {
    const row = document.createElement('tr');
    columns.forEach(col => {
      const td = document.createElement('td');
      const value = item[col.key];
      td.textContent = value != null ? value : '';
      row.appendChild(td);
    });
    tbody.appendChild(row);
  });

  table.appendChild(tbody);
  container.appendChild(table);
}

async function loadResource(resource, tableId, columns, query = '') {
  try {
    const url = `${endpoints[resource]}${query ? `?${query}` : ''}`;
    const data = await fetch(url).then(r => r.json());
    const items = data.content ?? data;
    renderTable(tableId, items, columns);
  } catch (err) {
    setStatus(`${resource}Status`, `Erro ao carregar: ${err.message}`, true);
  }
}

function hookForms() {
  document.getElementById('curriculoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      titulo: form.titulo.value,
      experienciaProfissional: form.experienciaProfissional.value,
      habilidades: form.habilidades.value,
      formacao: form.formacao.value,
      projetos: form.projetos.value,
      links: form.links.value
    };
    try {
      await sendJson(endpoints.curriculos, 'POST', payload);
      setStatus('curriculoStatus', 'Currículo criado com sucesso!');
      loadCurriculos();
      form.reset();
    } catch (err) {
      setStatus('curriculoStatus', err.message, true);
    }
  });

  document.getElementById('curriculoUpdateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      titulo: form.titulo.value,
      experienciaProfissional: form.experienciaProfissional.value,
      habilidades: form.habilidades.value,
      formacao: form.formacao.value,
      projetos: form.projetos.value,
      links: form.links.value
    };
    const id = form.curriculoId.value;
    try {
      await sendJson(`${endpoints.curriculos}/${id}`, 'PUT', payload);
      setStatus('curriculoStatus', 'Currículo atualizado com sucesso!');
      loadCurriculos();
      form.reset();
    } catch (err) {
      setStatus('curriculoStatus', err.message, true);
    }
  });

  document.getElementById('curriculoDelete').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = e.target.curriculoDeleteId.value;
    try {
      await sendJson(`${endpoints.curriculos}/${id}`, 'DELETE');
      setStatus('curriculoStatus', 'Currículo deletado com sucesso!');
      loadCurriculos();
      e.target.reset();
    } catch (err) {
      setStatus('curriculoStatus', err.message, true);
    }
  });

  document.getElementById('cursoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      nome: form.nome.value,
      descricao: form.descricao.value,
      link: form.link.value,
      area: form.area.value
    };
    try {
      await sendJson(endpoints.cursos, 'POST', payload);
      setStatus('cursoStatus', 'Curso criado com sucesso!');
      loadCursos();
      form.reset();
    } catch (err) {
      setStatus('cursoStatus', err.message, true);
    }
  });

  document.getElementById('cursoUpdateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const id = form.cursoId.value;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      nome: form.nome.value,
      descricao: form.descricao.value,
      link: form.link.value,
      area: form.area.value
    };
    try {
      await sendJson(`${endpoints.cursos}/${id}`, 'PUT', payload);
      setStatus('cursoStatus', 'Curso atualizado com sucesso!');
      loadCursos();
      form.reset();
    } catch (err) {
      setStatus('cursoStatus', err.message, true);
    }
  });

  document.getElementById('cursoDelete').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = e.target.cursoDeleteId.value;
    try {
      await sendJson(`${endpoints.cursos}/${id}`, 'DELETE');
      setStatus('cursoStatus', 'Curso removido.');
      loadCursos();
      e.target.reset();
    } catch (err) {
      setStatus('cursoStatus', err.message, true);
    }
  });

  document.getElementById('bemEstarForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      dataRegistro: form.dataRegistro.value ? `${form.dataRegistro.value}:00` : null,
      nivelHumor: form.nivelHumor.value ? Number(form.nivelHumor.value) : null,
      horasEstudo: form.horasEstudo.value ? `${form.horasEstudo.value}:00` : null,
      descricaoAtividade: form.descricaoAtividade.value
    };
    try {
      await sendJson(endpoints.bemEstar, 'POST', payload);
      setStatus('bemEstarStatus', 'Registro de bem-estar criado!');
      loadBemEstar();
      form.reset();
    } catch (err) {
      setStatus('bemEstarStatus', err.message, true);
    }
  });

  document.getElementById('bemEstarUpdateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const id = form.bemEstarId.value;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      dataRegistro: form.dataRegistro.value ? `${form.dataRegistro.value}:00` : null,
      nivelHumor: form.nivelHumor.value ? Number(form.nivelHumor.value) : null,
      horasEstudo: form.horasEstudo.value ? `${form.horasEstudo.value}:00` : null,
      descricaoAtividade: form.descricaoAtividade.value
    };
    try {
      await sendJson(`${endpoints.bemEstar}/${id}`, 'PUT', payload);
      setStatus('bemEstarStatus', 'Registro atualizado!');
      loadBemEstar();
      form.reset();
    } catch (err) {
      setStatus('bemEstarStatus', err.message, true);
    }
  });

  document.getElementById('bemEstarDelete').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = e.target.bemEstarDeleteId.value;
    try {
      await sendJson(`${endpoints.bemEstar}/${id}`, 'DELETE');
      setStatus('bemEstarStatus', 'Registro de bem-estar removido.');
      loadBemEstar();
      e.target.reset();
    } catch (err) {
      setStatus('bemEstarStatus', err.message, true);
    }
  });

  document.getElementById('trilhaForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      titulo: form.titulo.value,
      categoria: form.categoria.value
    };
    try {
      await sendJson(endpoints.trilhas, 'POST', payload);
      setStatus('trilhaStatus', 'Trilha criada!');
      loadTrilhas();
      form.reset();
    } catch (err) {
      setStatus('trilhaStatus', err.message, true);
    }
  });

  document.getElementById('trilhaUpdateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const id = form.trilhaId.value;
    const payload = {
      usuarioId: Number(form.usuarioId.value),
      titulo: form.titulo.value,
      categoria: form.categoria.value
    };
    try {
      await sendJson(`${endpoints.trilhas}/${id}`, 'PUT', payload);
      setStatus('trilhaStatus', 'Trilha atualizada!');
      loadTrilhas();
      form.reset();
    } catch (err) {
      setStatus('trilhaStatus', err.message, true);
    }
  });

  document.getElementById('trilhaDelete').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = e.target.trilhaDeleteId.value;
    try {
      await sendJson(`${endpoints.trilhas}/${id}`, 'DELETE');
      setStatus('trilhaStatus', 'Trilha removida.');
      loadTrilhas();
      e.target.reset();
    } catch (err) {
      setStatus('trilhaStatus', err.message, true);
    }
  });

  document.getElementById('objetivoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const payload = {
      trilhaId: Number(form.trilhaId.value),
      titulo: form.titulo.value,
      categoria: form.categoria.value,
      concluido: form.concluido.value
    };
    try {
      await sendJson(endpoints.objetivos, 'POST', payload);
      setStatus('objetivoStatus', 'Objetivo criado!');
      loadObjetivos();
      form.reset();
    } catch (err) {
      setStatus('objetivoStatus', err.message, true);
    }
  });

  document.getElementById('objetivoUpdateForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const id = form.objetivoId.value;
    const payload = {
      trilhaId: Number(form.trilhaId.value),
      titulo: form.titulo.value,
      categoria: form.categoria.value,
      concluido: form.concluido.value
    };
    try {
      await sendJson(`${endpoints.objetivos}/${id}`, 'PUT', payload);
      setStatus('objetivoStatus', 'Objetivo atualizado!');
      loadObjetivos();
      form.reset();
    } catch (err) {
      setStatus('objetivoStatus', err.message, true);
    }
  });

  document.getElementById('objetivoDelete').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = e.target.objetivoDeleteId.value;
    try {
      await sendJson(`${endpoints.objetivos}/${id}`, 'DELETE');
      setStatus('objetivoStatus', 'Objetivo removido.');
      loadObjetivos();
      e.target.reset();
    } catch (err) {
      setStatus('objetivoStatus', err.message, true);
    }
  });
}

function loadCurriculos() {
  loadResource('curriculos', 'curriculosTable', [
    { key: 'id', label: 'ID' },
    { key: 'usuarioId', label: 'Usuário' },
    { key: 'titulo', label: 'Título' },
    { key: 'formacao', label: 'Formação' },
    { key: 'habilidades', label: 'Habilidades' },
    { key: 'projetos', label: 'Projetos' },
    { key: 'links', label: 'Links' }
  ]);
}

function loadCursos() {
  loadResource('cursos', 'cursosTable', [
    { key: 'id', label: 'ID' },
    { key: 'usuarioId', label: 'Usuário' },
    { key: 'nome', label: 'Nome' },
    { key: 'descricao', label: 'Descrição' },
    { key: 'link', label: 'Link' },
    { key: 'area', label: 'Área' }
  ]);
}

function loadBemEstar() {
  loadResource('bemEstar', 'bemEstarTable', [
    { key: 'id', label: 'ID' },
    { key: 'usuarioId', label: 'Usuário' },
    { key: 'dataRegistro', label: 'Data Registro' },
    { key: 'nivelHumor', label: 'Humor' },
    { key: 'horasEstudo', label: 'Horas de Estudo' },
    { key: 'descricaoAtividade', label: 'Descrição' }
  ]);
}

function loadTrilhas() {
  loadResource('trilhas', 'trilhasTable', [
    { key: 'id', label: 'ID' },
    { key: 'usuarioId', label: 'Usuário' },
    { key: 'titulo', label: 'Título' },
    { key: 'categoria', label: 'Categoria' }
  ]);
}

function loadObjetivos() {
  loadResource('objetivos', 'objetivosTable', [
    { key: 'id', label: 'ID' },
    { key: 'trilhaId', label: 'Trilha' },
    { key: 'titulo', label: 'Título' },
    { key: 'categoria', label: 'Categoria' },
    { key: 'concluido', label: 'Concluído' }
  ]);
}

function hookLoadButtons() {
  document.getElementById('loadCurriculos').addEventListener('click', loadCurriculos);
  document.getElementById('loadCursos').addEventListener('click', loadCursos);
  document.getElementById('loadBemEstar').addEventListener('click', loadBemEstar);
  document.getElementById('loadTrilhas').addEventListener('click', loadTrilhas);
  document.getElementById('loadObjetivos').addEventListener('click', loadObjetivos);
}

document.addEventListener('DOMContentLoaded', () => {
  hookForms();
  hookLoadButtons();
  loadCurriculos();
  loadCursos();
  loadBemEstar();
  loadTrilhas();
  loadObjetivos();
});
