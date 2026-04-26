//
// Dá fetch POST na API enviando o valor de email e senha, pega token para validação
//

const API = 'http://localhost:8080/api';

async function login()
{
    const email = document.getElementById('email').value; // pega do html
    const senha = document.getElementById('senha').value;

    const res = await fetch(`${API}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password: senha }) // envia os valores no body
    });

    if (res.ok) {
        const data = await res.json();
        localStorage.setItem('token', data.token); // coloca a resposta (o token) na cache, dai você pode entrar em outros campos da aplicação
        window.location.href = 'produtos.html';
    } else {
        alert('Email ou senha inválidos');
    }
}