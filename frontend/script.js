
    // Olá para a pessoa fazendo esse js, aqui é o miguel
    // O endpoint que a API usa pra verificar login é http://localhost:8080/api/auth/login
    // Você precisa dar um fetch() com header POST para a API, depois transformar a resposta em um json (ela vem como promise)
    // Depois você trata a resposta fazendo alguma coisa, talvez uma mensagem de sucesso (desde que no fim ele troque pra tabela)
    // Importante: No final ele tem que receber a resposta e, se for OK, ir para outra interface, que é a tabela.
    // Já peguei os IDs do HTML pra te ajudar
    // Caso tenha dúvida, só pesquisa "Fetch API POST via JS" ou algo do tipo. Ou me manda mensagem lá no grupo.

function entrar()
{
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
    // TODO: Dar fetch POST no endpoint (Tem que ser POST por questão de segurança)
}

// IMPORTANTE: Como NÃO vai ter cadastro, avisa pro outro colega que tá fazendo a interface pra adicionar uma nota
// Na interface falando qual é o email e senha do usuário já existente.
// Email admin@gmail.com e senha admin


// Aqui vai todas as rotas que eu defini que você vai acabar usando eventualmente:
//
// POST http://localhost:8080/api/auth/login (Esse eu já falei, é o de login)
//
// GET    http://localhost:8080/api/products              <- Busca todos os produtos
// GET    http://localhost:8080/api/products/{id}         <- Para pegar um produto em específico
// POST   http://localhost:8080/api/products              <- Cria um Produto
// PUT    http://localhost:8080/api/products/{id}         <- Atualiza um produto
// DELETE http://localhost:8080/api/products/{id}         <- Exclui um produto
//
// GET    http://localhost:8080/api/categories            <- Procura uma categoria
// POST   http://localhost:8080/api/categories            <- Cria uma categoria