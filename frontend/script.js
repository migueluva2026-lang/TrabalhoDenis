

function entrar() {
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    if (!nome || !email || !senha) {
        document.getElementById("mensagem").innerText = "Preencha todos os campos!";
        document.getElementById("mensagem").style.color = "red";
        return;
    }

    // Simulação de login
    document.getElementById("mensagem").innerText = "Login realizado com sucesso!";
    document.getElementById("mensagem").style.color = "lightgreen";
}