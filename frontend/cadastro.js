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

    if (editId) {
        loadProduct();
    }
}

async function loadProduct()
{
    const res = await fetch(`${API}/products/${editId}`, { headers: headers() });
    const product = await res.json();

    document.getElementById('nome').value = product.name;
    document.getElementById('preco').value = product.price;
    document.getElementById('quantidade').value = product.stockQuantity;
    document.getElementById('descricao').value = product.description || '';
    if (p.category) document.getElementById('categoria').value = product.category.id;

    document.querySelector('h1').textContent = 'Editar Produto';
    document.querySelector('.enviar').textContent = 'Salvar Alterações';
}

document.querySelector('.formulario').addEventListener('submit', async (e) => {
    e.preventDefault(); // Evita funcionamento padrão do browser

    const body = {
        name: document.getElementById('nome').value,
        price: parseFloat(document.getElementById('preco').value),
        stockQuantity: parseInt(document.getElementById('quantidade').value),
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