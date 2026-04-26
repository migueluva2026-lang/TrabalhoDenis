//
// Dá fetch POST na API enviando o valor de email e senha, pega token para validação
//

const API = 'http://localhost:8080/api';

async function login()
{
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    const res = await fetch(`${API}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password: senha })
    });

    if (res.ok) {
        const data = await res.json();
        localStorage.setItem('token', data.token);
        window.location.href = 'produtos.html';
    } else {
        alert('Email ou senha inválidos');
    }
}