# getservices-backend

API REST em Spring Boot para integração com o front-end da Get Services.

## Tecnologias

- Java 17+
- Spring Boot 3.3.4
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security Crypto, usado apenas para BCrypt
- MySQL
- React

## Configuração do banco

Execute o script:

```text
database/getservices_schema.sql
```

Depois ajuste o arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/getservices?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=123456
```

## Rodando o projeto

```bash
mvn spring-boot:run
```

Servidor:

```text
http://localhost:8080
```

## Endpoints

### Criar cadastro

```http
POST /api/auth/cadastro
```

Body cliente:

```json
{
  "nome": "Pedro Gustavo",
  "email": "pedro@email.com",
  "senha": "123456",
  "tipoUsuario": "cliente"
}
```

Body profissional:

```json
{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "senha": "123456",
  "tipoUsuario": "profissional",
  "areaAtuacao": "Elétrica"
}
```

### Login

```http
POST /api/auth/login
```

```json
{
  "email": "pedro@email.com",
  "senha": "123456",
  "tipoUsuario": "cliente"
}
```

## Integração com o front

O front usa:

```js
const API_URL = 'http://localhost:8080/api';
```

Arquivos integrados:

```text
GetServices/login/login.html
GetServices/cadastro/cadastro.html
```

## CORS

O backend libera chamadas vindas de:

```text
http://127.0.0.1:5500
http://localhost:5500
http://localhost:8000
http://127.0.0.1:8000
```

## Orçamentos e serviços integrados

Este pacote também deixa prontas as rotas usadas pelas páginas de serviços do front:

```http
GET /api/servicos
POST /api/servicos
POST /api/orcamentos
```

Para testar rapidamente o protótipo sem login:

1. Execute `database/getservices_schema.sql` no MySQL.
2. O script cria o cliente de teste `id = 1`.
3. O script cria os 16 serviços usados nas páginas de Limpeza, Elétrica, Construção e Informática.
4. Rode o back-end com `mvn spring-boot:run`.
5. Abra o front com Live Server na porta 5500.
6. Clique em **Solicitar Orçamento** em alguma página de serviço.

A solicitação será salva na tabela:

```text
solicitacoes_orcamento
```

O arquivo do front responsável por isso é:

```text
GetServices/pagina_servicos/solicitacoes-orcamento.js
```
