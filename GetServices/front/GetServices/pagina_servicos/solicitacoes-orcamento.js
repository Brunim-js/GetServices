// Integração: páginas de serviços -> Spring Boot -> MySQL
// Endpoints usados:
// GET  http://localhost:8080/api/servicos
// POST http://localhost:8080/api/orcamentos

const API_BASE = "http://localhost:8080/api";
const API_SERVICOS = `${API_BASE}/servicos`;
const API_ORCAMENTOS = `${API_BASE}/orcamentos`;

// Protótipo sem login: esse cliente precisa existir na tabela usuarios.
// O script database/getservices_schema.sql já cria esse cliente com id = 1.
const CLIENTE_TESTE_ID = 1;

// Mapa local usado como plano B. O ideal é buscar os IDs reais no banco pelo GET /api/servicos.
let servicosPorTitulo = {
    "Limpeza Residencial": 1,
    "Limpeza Comercial": 2,
    "Limpeza Pós-Obra": 3,
    "Limpeza de Estofados": 4,

    "Instalações Elétricas": 5,
    "Manutenção Elétrica": 6,
    "Instalação de Equipamentos Elétricos": 7,
    "Inspeção e Segurança Elétrica": 8,

    "Construção Residencial": 9,
    "Reformas e Acabamentos": 10,
    "Alvenaria e Reparos": 11,
    "Projetos e Consultoria": 12,

    "Manutenção de Computadores": 13,
    "Instalação e Config de Redes": 14,
    "Suporte Técnico e Segurança Digital": 15,
    "Desenvolvimento de Sites": 16
};

function normalizarTexto(texto) {
    return texto
        .trim()
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
}

async function carregarServicosDoBanco() {
    try {
        const resposta = await fetch(API_SERVICOS);

        if (!resposta.ok) {
            console.warn("Não foi possível carregar /api/servicos. Usando IDs locais de teste.");
            return;
        }

        const servicos = await resposta.json();
        const mapaAtualizado = {};

        servicos.forEach(function (servico) {
            mapaAtualizado[normalizarTexto(servico.titulo)] = servico.id;
        });

        servicosPorTitulo = {
            ...servicosPorTitulo,
            ...mapaAtualizado
        };

        console.log("Serviços carregados do banco:", servicos);
    } catch (erro) {
        console.warn("Back-end indisponível. Usando IDs locais de teste.", erro);
    }
}

function pegarTituloDoCard(botao) {
    const card = botao.closest(".card-gs, .service-card, .card, .tab-pane, body");
    const titulo = card?.querySelector("h3")?.innerText?.trim();
    return titulo || "Serviço não identificado";
}

async function enviarSolicitacaoOrcamento(nomeServico, botaoClicado) {
    const chaveServico = normalizarTexto(nomeServico);
    const servicoId = servicosPorTitulo[chaveServico] || servicosPorTitulo[nomeServico];

    if (!servicoId) {
        alert(`Não encontrei esse serviço no banco: ${nomeServico}`);
        return;
    }

    const descricao = prompt(`Descreva o que você precisa para: ${nomeServico}`);

    if (!descricao || descricao.trim() === "") {
        alert("A descrição é obrigatória para solicitar o orçamento.");
        return;
    }

    const solicitacao = {
        clienteId: CLIENTE_TESTE_ID,
        servicoId: servicoId,
        descricao: descricao.trim()
    };

    try {
        if (botaoClicado) {
            botaoClicado.disabled = true;
            botaoClicado.innerText = "Enviando...";
        }

        const resposta = await fetch(API_ORCAMENTOS, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(solicitacao)
        });

        let dados = null;
        try {
            dados = await resposta.json();
        } catch (erroJson) {
            dados = null;
        }

        if (!resposta.ok) {
            const mensagemErro = dados?.erro || dados?.message || "Erro ao solicitar orçamento.";
            alert(mensagemErro);
            console.error("Erro no POST /api/orcamentos:", dados);
            return;
        }

        alert("Solicitação de orçamento enviada com sucesso!");
        console.log("Solicitação salva no banco:", dados);
    } catch (erro) {
        alert("Não foi possível conectar ao back-end. Verifique se o Spring Boot está rodando na porta 8080.");
        console.error("Erro de conexão:", erro);
    } finally {
        if (botaoClicado) {
            botaoClicado.disabled = false;
            botaoClicado.innerText = "Solicitar Orçamento";
        }
    }
}

// Mantém compatibilidade caso alguma página ainda chame onclick="solicitar('...')".
function solicitar(nomeServico) {
    enviarSolicitacaoOrcamento(nomeServico, null);
}

document.addEventListener("DOMContentLoaded", async function () {
    await carregarServicosDoBanco();

    const botoes = document.querySelectorAll(".btn-solicitar");

    botoes.forEach(function (botao) {
        botao.onclick = function () {
            const nomeServico = pegarTituloDoCard(botao);
            enviarSolicitacaoOrcamento(nomeServico, botao);
        };
    });
});
