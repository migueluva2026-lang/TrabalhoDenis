//
// Carrega Dinâmicamente os produtos baseado no DB, também faz o sort baseado na categoriaSelecionada
// OBS: O token tem persistência por cache, se você logar uma vez ele vai ficar por um tempo no browser
// OBS2: O token faz com que, caso você tente entrar em algum html sem logar, ele te move pra pagina de login
// Se quiser testar é só dar localStorage.clear() no console do F12, isso limpa o token e você pode tentar entrar em outro html sem logar
//

const API = 'http://localhost:8080/api';

function token() { return localStorage.getItem('token'); }
function headers() { return { 'Authorization': `Bearer ${token()}`, 'Content-Type': 'application/json' }; }

if (!token()) window.location.href = 'login.html';

let categoriaSelecionada = 'todos';

async function loadFilters()
{
    const res = await fetch(`${API}/categories`, { headers: headers() });
    const categorias = await res.json();

    const filtros = document.getElementById('filtros');

    const btnTodos = createFilter('Todos', 'todos');
    btnTodos.classList.add('ativo');
    filtros.appendChild(btnTodos);

    categorias.forEach(categoria =>
        filtros.appendChild(createFilter(categoria.name, categoria.id))
    );
}

function createFilter(nome, id)
{
    const btn = document.createElement('button');
    btn.textContent = nome;
    btn.className = 'filtro-btn';
    btn.onclick = () => {
        document.querySelectorAll('.filtro-btn').forEach(botao => botao.classList.remove('ativo'));
        btn.classList.add('ativo');
        categoriaSelecionada = String(id);
        sortCards();
    };
    return btn;
}

function sortCards() // Esconde/mostra os cards de acordo com suas categorias
{
    document.querySelectorAll('.card').forEach(card => {
        const visivel = categoriaSelecionada === 'todos' || card.dataset.categoria === categoriaSelecionada;
        card.style.display = visivel ? 'block' : 'none';
    });
}

async function loadProducts()
{
    const res = await fetch(`${API}/products`, { headers: headers() });
    const produtos = await res.json();

    const container = document.getElementById('container');
    container.innerHTML = '';

    produtos.forEach(product => {
        const card = document.createElement('div');
        card.className = 'card';
        card.dataset.categoria = product.category ? String(product.category.id) : ''; // Guarda o id da categoria no card pra o sortCards() conseguir filtrar

        card.innerHTML = `
            <h2>${product.name}</h2>
            <p class="descricao">${product.description || ''}</p>
            <p class="preco">R$ ${product.price.toFixed(2).replace('.', ',')}</p>
            <p class="estoque">Estoque: ${product.stockQuantity}</p>
            <div class="card-acoes">
                <button class="btn-editar" onclick="window.location.href='cadastro.html?id=${product.id}'">Editar</button>
                <button class="btn-deletar" onclick="deleteProduct(${product.id})">Remover</button>
            </div>
        `;

        container.appendChild(card);
    });

    filtrarCards();
}

async function deleteProduct(id)
{
    if (!confirm('Remover este produto?')) {
        return;
    }
    await fetch(`${API}/products/${id}`, { method: 'DELETE', headers: headers() });
    loadProducts();
}

loadFilters();
loadProducts();