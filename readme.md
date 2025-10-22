# Restful API Automated Tests (Java + RestAssured)

## 🧩 Descrição técnica
Este projeto é uma **suíte de testes automatizados** desenvolvida em **Java 17**, utilizando **RestAssured** e **JUnit 5**, com o objetivo de validar o comportamento da API pública [`https://restful-api.dev/`](https://restful-api.dev/).  
Ele cobre operações **CRUD completas**, validações de **status code**, **tempo de resposta**, **headers** e **tratamento de erros**.

---

## ⚙️ Tecnologias utilizadas
- **Java 17** – Linguagem principal
- **Maven** – Gerenciador de dependências e build
- **JUnit 5** – Framework de testes
- **RestAssured** – Biblioteca para automação de testes de APIs REST
- **Hamcrest** – Matchers para validações mais legíveis

---

## 🧠 Estrutura do projeto
```
restful-api-dev-tests-java/
├── pom.xml                    # Configuração Maven e dependências
├── README.md                  # Documentação do projeto
└── src
    └── test
        └── java
            └── tests
                └── RestfulApiTests.java  # Classe principal de testes
```

---

## 🚀 Configuração e execução
### 1. Pré-requisitos
- Java 17 ou superior instalado
- Maven instalado e configurado (com variável de ambiente `MAVEN_HOME`)
- Conexão com a internet (a API é pública e online)

Verifique as versões com:
```bash
java -version
mvn -version
```

### 2. Clonar o repositório
```bash
git clone https://github.com/<seu-usuario>/restful-api-dev-tests-java.git
cd restful-api-dev-tests-java
```

### 3. Executar os testes
```bash
mvn test
```
Os resultados dos testes serão exibidos no console, com relatórios de sucesso/falha.

### 4. Rodar um teste específico
```bash
mvn -Dtest=RestfulApiTests#testCreateObject test
```

---

## 🧪 Escopo dos testes
A suíte cobre **15 casos de teste automatizados**, incluindo:

| # | Categoria | Descrição | Método |
|---|------------|------------|---------|
| 1 | GET | Retorna todos os objetos existentes | `testGetAllObjects()` |
| 2 | POST | Cria um novo objeto válido | `testCreateObject()` |
| 3 | GET | Busca o objeto recém-criado | `testGetCreatedObject()` |
| 4 | PUT | Atualiza completamente o objeto | `testUpdateObject()` |
| 5 | PATCH | Atualiza parcialmente o objeto | `testPartialUpdateObject()` |
| 6 | GET | Verifica resposta para ID inválido | `testGetInvalidObject()` |
| 7 | GET | Garante que o objeto criado está listado | `testListObjectsContainsId()` |
| 8 | PUT | Testa atualização com JSON inválido | `testUpdateWithInvalidData()` |
| 9 | POST | Tenta criar objeto sem nome | `testCreateWithoutName()` |
| 10 | HEADERS | Valida Content-Type da resposta | `testContentTypeHeader()` |
| 11 | PERFORMANCE | Valida tempo máximo de resposta (3s) | `testResponseTime()` |
| 12 | LOOP | Cria múltiplos objetos em sequência | `testCreateMultipleObjects()` |
| 13 | GET | Confirma aumento no número total de objetos | `testGetAllObjectsAgain()` |
| 14 | DELETE | Deleta o objeto criado | `testDeleteObject()` |
| 15 | GET | Garante que o objeto deletado não existe mais | `testDeletedObjectNotFound()` |

---

## 🧱 Commits semânticos (padrão adotado)
O projeto segue o padrão de **commits semânticos**, para manter histórico limpo e fácil de rastrear:

| Tipo | Descrição | Exemplo |
|------|------------|----------|
| **feat** | Nova funcionalidade | `feat: adiciona testes de PATCH` |
| **test** | Criação ou modificação de testes | `test: adiciona casos de erro 404 e 400` |
| **fix** | Correções de bugs em testes | `fix: corrige status code esperado em DELETE` |
| **docs** | Alterações na documentação | `docs: adiciona instruções de execução no README` |
| **refactor** | Refatoração sem alterar comportamento | `refactor: melhora organização dos métodos de teste` |
| **chore** | Configurações e manutenção | `chore: adiciona plugin Surefire no pom.xml` |

---

## 📊 Resultados esperados
Após a execução dos testes, o Maven exibirá um resumo como:
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running tests.RestfulApiTests
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

