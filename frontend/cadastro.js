//
// Carrega as informações do produto se já no DB, faz um PUT ou POST baseado se já existe também
// OBS: O token tem persistência por cache, se você logar uma vez ele vai ficar por um tempo no browser
// OBS2: O token faz com que, caso você tente entrar em algum html sem logar, ele te move pra pagina de login

const API = 'http://localhost:8080/api';

function token()
{
    return localStorage.getItem('token'); // Pega token no cache local
}

function headers()
{
    return { 'Authorization': `Bearer ${token()}`, 'Content-Type': 'application/json' }; // "Cabeçalho" obrigatório pela API, aqui só tem o token
}

if (!token()) window.location.href = 'login.html'; // Valida: Se não tenho token, não tenho acesso a pagina

const editId = new URLSearchParams(window.location.search).get('id');
let stockQuantity = 0;

async function loadCategories()
{
    const res = await fetch(`${API}/categories`, { headers: headers() });
    const categorias = await res.json();

    const select = document.getElementById('categoria');

    categorias.forEach(categoria => {
        const opcao = document.createElement('option');
        opcao.value = categoria.id;
        opcao.textContent = categoria.name;
        select.appendChild(opcao);
    });

    // Só carrega o produto depois que as categorias já estão no select
    // senão o campo de categoria não consegue ser meio que pré-selecionado
    if (editId) {
        loadProduct();
    }
}

function renderBotoesQntd() { // Renderiza os botões de quantidade caso esteja editando um produto
    const campo = document.getElementById('campo-quantidade');
    campo.innerHTML = `
        <div style="display:flex; align-items:center; gap:8px; flex-wrap:wrap;">
            <button type="button" onclick="ajustarQtd(-10)">-10</button>
            <button type="button" onclick="ajustarQtd(-1)">-1</button>
            <span id="qtd-display" style="min-width:40px; text-align:center; font-size:18px;">${quantidadeAtual}</span>
            <button type="button" onclick="ajustarQtd(1)">+1</button>
            <button type="button" onclick="ajustarQtd(10)">+10</button>
        </div>
    `;
}

function ajustarQtd(valor) {
    quantidadeAtual = Math.max(0, quantidadeAtual + valor);
    document.getElementById('qtd-display').textContent = quantidadeAtual;
}

async function loadProduct()
{
    const res = await fetch(`${API}/products/${editId}`, { headers: headers() });
    const product = await res.json();

    document.getElementById('nome').value = product.name;
    document.getElementById('preco').value = product.price;
    document.getElementById('quantidade').value = product.stockQuantity;
    document.getElementById('descricao').value = product.description || '';

    if (product.category) document.getElementById('categoria').value = product.category.id;

    quantidadeAtual = product.stockQuantity;
    renderBotoesQntd();

    document.querySelector('h1').textContent = 'Editar Produto';
    document.querySelector('.enviar').textContent = 'Salvar Alterações';
}

document.querySelector('.formulario').addEventListener('submit', async (e) => {
    e.preventDefault(); // Evita funcionamento padrão do browser

    const qtd = editId ? quantidadeAtual: parseInt(document.getElementById('quantidade').value);

    const body = {
        name: document.getElementById('nome').value,
        price: parseFloat(document.getElementById('preco').value),
        stockQuantity: qtd,
        description: document.getElementById('descricao').value,
        category: { id: parseInt(document.getElementById('categoria').value) }
    };

    const url = editId ? `${API}/products/${editId}` : `${API}/products`;
    const method = editId ? 'PUT' : 'POST';

    const res = await fetch(url, { method, headers: headers(), body: JSON.stringify(body) });

    if (res.ok) {
        window.location.href = 'produtos.html';
    } else {
        alert('Erro ao salvar produto');
    }
});

loadCategories();