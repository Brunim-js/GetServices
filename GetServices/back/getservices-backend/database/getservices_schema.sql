CREATE DATABASE IF NOT EXISTS getservices
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE getservices;

DROP TABLE IF EXISTS solicitacoes_orcamento;
DROP TABLE IF EXISTS servicos;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('CLIENTE', 'PROFISSIONAL') NOT NULL,
    area_atuacao VARCHAR(100),
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE servicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descricao TEXT,
    categoria_id BIGINT NOT NULL,
    profissional_id BIGINT NOT NULL,
    CONSTRAINT fk_servico_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_servico_profissional FOREIGN KEY (profissional_id) REFERENCES usuarios(id)
);

CREATE TABLE solicitacoes_orcamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    descricao TEXT NOT NULL,
    status ENUM('PENDENTE', 'ACEITA', 'RECUSADA', 'CONCLUIDA') NOT NULL DEFAULT 'PENDENTE',
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orcamento_cliente FOREIGN KEY (cliente_id) REFERENCES usuarios(id),
    CONSTRAINT fk_orcamento_servico FOREIGN KEY (servico_id) REFERENCES servicos(id)
);

INSERT INTO categorias (id, nome) VALUES
(1, 'Limpeza'),
(2, 'Elétrica'),
(3, 'Construção'),
(4, 'Informática');

-- Usuários de teste para o protótipo.
-- O front de orçamento usa cliente_id = 1 sem login.
INSERT INTO usuarios (id, nome, email, senha_hash, tipo_usuario, area_atuacao) VALUES
(1, 'Cliente Teste', 'cliente.teste@getservices.com', 'senha_teste_nao_usada_no_prototipo', 'CLIENTE', NULL),
(2, 'Profissional Limpeza', 'limpeza@getservices.com', 'senha_teste_nao_usada_no_prototipo', 'PROFISSIONAL', 'Limpeza'),
(3, 'Profissional Elétrica', 'eletrica@getservices.com', 'senha_teste_nao_usada_no_prototipo', 'PROFISSIONAL', 'Elétrica'),
(4, 'Profissional Construção', 'construcao@getservices.com', 'senha_teste_nao_usada_no_prototipo', 'PROFISSIONAL', 'Construção'),
(5, 'Profissional Informática', 'informatica@getservices.com', 'senha_teste_nao_usada_no_prototipo', 'PROFISSIONAL', 'Informática');

-- Serviços de teste usados pelos botões do front.
INSERT INTO servicos (id, titulo, descricao, categoria_id, profissional_id) VALUES
(1, 'Limpeza Residencial', 'Limpeza para casas e apartamentos.', 1, 2),
(2, 'Limpeza Comercial', 'Limpeza para escritórios e lojas.', 1, 2),
(3, 'Limpeza Pós-Obra', 'Limpeza pesada após obras.', 1, 2),
(4, 'Limpeza de Estofados', 'Limpeza de sofás, poltronas e colchões.', 1, 2),

(5, 'Instalações Elétricas', 'Instalação elétrica para imóveis.', 2, 3),
(6, 'Manutenção Elétrica', 'Correção de falhas e reparos elétricos.', 2, 3),
(7, 'Instalação de Equipamentos Elétricos', 'Instalação de chuveiros, luminárias e equipamentos.', 2, 3),
(8, 'Inspeção e Segurança Elétrica', 'Revisão preventiva e segurança elétrica.', 2, 3),

(9, 'Construção Residencial', 'Serviços de construção residencial.', 3, 4),
(10, 'Reformas e Acabamentos', 'Reformas, pintura e acabamentos.', 3, 4),
(11, 'Alvenaria e Reparos', 'Alvenaria, reparos e pequenos consertos.', 3, 4),
(12, 'Projetos e Consultoria', 'Consultoria e planejamento de obras.', 3, 4),

(13, 'Manutenção de Computadores', 'Formatação, limpeza e manutenção de computadores.', 4, 5),
(14, 'Instalação e Config de Redes', 'Configuração de internet, roteadores e redes.', 4, 5),
(15, 'Suporte Técnico e Segurança Digital', 'Suporte técnico e proteção digital.', 4, 5),
(16, 'Desenvolvimento de Sites', 'Criação de sites para negócios e profissionais.', 4, 5);
